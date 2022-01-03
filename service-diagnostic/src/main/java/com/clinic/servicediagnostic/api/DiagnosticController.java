package com.clinic.servicediagnostic.api;


import com.clinic.servicediagnostic.repo.model.Diagnostic;
import com.clinic.servicediagnostic.service.impl.DiagnosticServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clinic")
public class DiagnosticController {

    private final DiagnosticServiceImpl diagnosticService;

    @GetMapping
    public ResponseEntity<List<Diagnostic>> index(){
        return ResponseEntity.ok(diagnosticService.getAllDiagnostic());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Diagnostic> showById(@PathVariable long id){
        try{
            final Diagnostic diagnostic = diagnosticService.getDiagnosticById(id);
            return ResponseEntity.ok(diagnostic);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Diagnostic diagnostic){
        final long clinicId = diagnosticService.createDiagnostic(diagnostic);
        final String clinicUri = String.format("/clinic/%d", clinicId);
        return ResponseEntity.created(URI.create(clinicUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Diagnostic diagnostic){
        final long idPatient = diagnostic.getIdPatient();
        final String complains = diagnostic.getComplains();
        final String conclusion = diagnostic.getConclusion();
        final String diagnose = diagnostic.getDiagnose();
        final double price = diagnostic.getPrice();
        final String additionalReview = diagnostic.getAdditionalReview();

        try {
            diagnosticService.updateDiagnostic(id, idPatient, complains, conclusion, diagnose, price, additionalReview);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
        diagnosticService.deleteDiagnostic(id);
        return ResponseEntity.noContent().build();
    }
}
