package com.clinic.servicediagnostic.service.impl;

import com.clinic.servicediagnostic.api.dto.UserDto;
import com.clinic.servicediagnostic.repo.DiagnosticRepo;
import com.clinic.servicediagnostic.repo.model.Diagnostic;
import com.clinic.servicediagnostic.service.DiagnosticService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class DiagnosticServiceImpl implements DiagnosticService {

    private final DiagnosticRepo diagnosticRepo;
    final RestTemplate restTemplate = new RestTemplate();
    private final String userUrl = "http://service-identity:8081/users/";


    @Override
    public List<Diagnostic> getAllDiagnostic() {
        return diagnosticRepo.findAll();
    }

    @Override
    public Diagnostic getDiagnosticById(long id) {
        final HttpEntity<Long> requestPatient = new HttpEntity<>(id);
        final ResponseEntity<UserDto> response = restTemplate.exchange(userUrl + id, HttpMethod.GET, requestPatient, UserDto.class,
                new ParameterizedTypeReference<UserDto>() {});
        if (response.getStatusCode()== HttpStatus.NOT_FOUND){
            throw new IllegalArgumentException(String.format("Patient with id = %d not found", id));
        }
        final Optional<Diagnostic> maybeDiagnostic = diagnosticRepo.findById(id);
        if(maybeDiagnostic.isEmpty()) throw new IllegalArgumentException("Invalid Id");
        return maybeDiagnostic.get();
    }

    @Override
    public long createDiagnostic(Diagnostic diagnostic) {
        final Diagnostic savedDiagnostic = diagnosticRepo.save(diagnostic);
        return savedDiagnostic.getId();
    }

    @Override
    public void updateDiagnostic(long id, long idPatient, String complains, String conclusion,
                                 String diagnose, double price, String additionalReview) {
        final Optional<Diagnostic> maybeDiagnostic = diagnosticRepo.findById(id);
        if(maybeDiagnostic.isEmpty()) throw new IllegalArgumentException("Invalid Id");

        final Diagnostic diagnostic = maybeDiagnostic.get();
        diagnostic.setIdPatient(idPatient);
        if(complains!=null && !complains.isBlank()) diagnostic.setComplains(complains);
        if(conclusion!=null && !conclusion.isBlank()) diagnostic.setConclusion(conclusion);
        if(diagnose!=null && !diagnose.isBlank()) diagnostic.setDiagnose(diagnose);
        if(price!=0) diagnostic.setPrice(price);
        if(additionalReview != null && !additionalReview.isBlank()) diagnostic.setAdditionalReview(additionalReview);
        diagnostic.setDateUpdated(LocalDate.now());
    }

    @Override
    public void deleteDiagnostic(long id) {
        diagnosticRepo.deleteById(id);
    }
}
