package com.clinic.servicediagnostic.service;

import com.clinic.servicediagnostic.repo.model.Diagnostic;

import java.util.List;

public interface DiagnosticService {
    List<Diagnostic> getAllDiagnostic();
    Diagnostic getDiagnosticById(long id);
    long createDiagnostic(Diagnostic clinic);
    void updateDiagnostic(long id, long idPatient, String complains,
                          String conclusion, String diagnose, double price,
                          String additionalReview);
    void deleteDiagnostic(long id);
}
