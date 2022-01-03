package com.clinic.servicedeclaration.service.impl;

import com.clinic.servicedeclaration.api.dto.UserDto;
import com.clinic.servicedeclaration.repo.DeclarationRepo;
import com.clinic.servicedeclaration.repo.model.Declaration;
import com.clinic.servicedeclaration.service.DeclarationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DeclarationServiceImpl implements DeclarationService {

    final private DeclarationRepo declarationRepo;
    final RestTemplate restTemplate = new RestTemplate();
    private final String userUrl = "http://service-identity:8081/users/";

    @Override
    public List<Declaration> GetAllDeclarations() {
        return declarationRepo.findAll();
    }

    @Override
    public List<Declaration> GetDoctorPatient(long idDoctor) throws IllegalArgumentException{
        final HttpEntity<Long> request = new HttpEntity<>(idDoctor);
        final ResponseEntity<UserDto> response = restTemplate.exchange(userUrl + idDoctor, HttpMethod.GET,
                request, UserDto.class, new ParameterizedTypeReference<UserDto>() {});

        if (response.getStatusCode()== HttpStatus.NOT_FOUND){
            throw new IllegalArgumentException("User with this email not found");
        }

        return declarationRepo.findAllByIdDoctor(idDoctor);
    }

    @Override
    public List<Declaration> GetByDoctorId(long idDoctor) throws IllegalArgumentException{
        final HttpEntity<Long> requestDoctor = new HttpEntity<>(idDoctor);
        final ResponseEntity<UserDto> responseDoctor = restTemplate.exchange(userUrl + idDoctor, HttpMethod.GET, requestDoctor, UserDto.class,
                new ParameterizedTypeReference<UserDto>() {});
        if (responseDoctor.getStatusCode()== HttpStatus.NOT_FOUND){
            throw new IllegalArgumentException(String.format("Doctor with id = %d not found", idDoctor));
        }

        return declarationRepo.findAllByIdDoctor(idDoctor);
    }

    @Override
    public Declaration GetByPatient(long idPatient) throws IllegalArgumentException{
        final HttpEntity<Long> requestPatient = new HttpEntity<>(idPatient);
        final ResponseEntity<UserDto> response = restTemplate.exchange(userUrl + idPatient, HttpMethod.GET, requestPatient, UserDto.class,
                new ParameterizedTypeReference<UserDto>() {});
        if (response.getStatusCode()== HttpStatus.NOT_FOUND){
            throw new IllegalArgumentException(String.format("Patient with id = %d not found", idPatient));
        }
        final Optional<Declaration> maybeDecl = declarationRepo.findByIdPatient(idPatient);
        if(maybeDecl.isEmpty()) throw new IllegalArgumentException(String.format("Declaration with patient id = %d not found", idPatient));
        return maybeDecl.get();
    }

    @Override
    public long createDeclaration(long idPatient, long idDoctor) throws IllegalArgumentException{
        final HttpEntity<Long> requestPatient = new HttpEntity<>(idPatient);
        final ResponseEntity<UserDto> response = restTemplate.exchange(userUrl + idPatient, HttpMethod.GET, requestPatient, UserDto.class,
                new ParameterizedTypeReference<UserDto>() {});
        if (response.getStatusCode()== HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException(String.format("Patient with id = %d not found", idPatient));
        }

        final HttpEntity<Long> requestDoctor = new HttpEntity<>(idDoctor);
        final ResponseEntity<UserDto> responseDoctor = restTemplate.exchange(userUrl + idDoctor, HttpMethod.GET, requestDoctor, UserDto.class,
                new ParameterizedTypeReference<UserDto>() {});
        if (responseDoctor.getStatusCode()== HttpStatus.NOT_FOUND){
            throw new IllegalArgumentException(String.format("Doctor with id = %d not found", idDoctor));
        }

        if(idPatient != idDoctor) {
            Declaration declaration = new Declaration(idPatient, idDoctor);
            Declaration savedDeclaration = declarationRepo.save(declaration);
            return savedDeclaration.getIdPatient();
        } else throw new IllegalArgumentException("The doctor cannot sign a declaration with himself ");
    }

    @Override
    public void deleteDeclaration(long idPatient) {
        declarationRepo.deleteById(idPatient);
    }
}
