package com.clinic.servicevisits.service.impl;

import com.clinic.servicevisits.api.dto.DeclarationDto;
import com.clinic.servicevisits.api.dto.UserDto;
import com.clinic.servicevisits.repo.VisitRepo;
import com.clinic.servicevisits.repo.model.Visit;
import com.clinic.servicevisits.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VisitServiceImpl implements VisitService {
    private final VisitRepo visitRepo;
    final RestTemplate restTemplate = new RestTemplate();
    private final String userUrl = "http://service-identity:8081/users/";
    private final String declarationUrl = "http://service-declaration:8082/declarations/patient";

    @Override
    public List<Visit> getAllVisits() {
        return visitRepo.findAll();
    }

    @Override
    public Visit getVisitById(long id) throws IllegalArgumentException {
        final Optional<Visit> maybeVisit = visitRepo.findById(id);

        if (maybeVisit.isPresent())
            return maybeVisit.get();
        else
            throw new IllegalArgumentException("Invalid visit ID");
    }

    @Override
    public List<Visit> getVisitToday() {
        return visitRepo.getAllByDateVisit(LocalDate.now());
    }

    @Override
    public long createVisit(long idPatient, LocalDate dateVisit, LocalTime timeVisit) {
        final HttpEntity<Long> requestPatient = new HttpEntity<>(idPatient);
        final ResponseEntity<UserDto> response = restTemplate.exchange(userUrl + idPatient, HttpMethod.GET, requestPatient, UserDto.class,
                new ParameterizedTypeReference<UserDto>() {});
        if (response.getStatusCode()== HttpStatus.NOT_FOUND){
            throw new IllegalArgumentException(String.format("Patient with id = %d not found", idPatient));
        }

        final ResponseEntity<DeclarationDto> responseDeclaration = restTemplate.exchange(declarationUrl + idPatient,
                HttpMethod.GET, requestPatient, DeclarationDto.class,
                new ParameterizedTypeReference<DeclarationDto>() {});
        if (responseDeclaration.getStatusCode()== HttpStatus.NOT_FOUND){
            throw new IllegalArgumentException(String.format("Declaration with Patient id = %d not found", idPatient));
        }

        LocalTime start = LocalTime.parse( "08:30:00" );
        LocalTime stop = LocalTime.parse( "18:00:00" );
        Boolean isCorect = (timeVisit.isAfter(start) && timeVisit.isBefore(stop));
        if(isCorect) {
            final Visit visit = new Visit(idPatient, dateVisit, timeVisit);
            final Visit savedVisit = visitRepo.save(visit);

            return savedVisit.getId();
        } else throw new IllegalArgumentException("The clinic is open from 8:30 to 18:00");
    }

    @Override
    public void deleteVisit(long id) {
        visitRepo.deleteById(id);
    }
}
