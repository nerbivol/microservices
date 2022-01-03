package com.clinic.servicedeclaration.service;

import com.clinic.servicedeclaration.repo.model.Declaration;

import java.util.List;

public interface DeclarationService {
    List<Declaration> GetAllDeclarations();
    List<Declaration> GetDoctorPatient(long idDoctor) throws IllegalArgumentException;
    List<Declaration> GetByDoctorId(long idDoctor) throws IllegalArgumentException;
    Declaration GetByPatient(long idPatient) throws IllegalArgumentException;
    long createDeclaration(long idPatient,long idDoctor) throws IllegalArgumentException;
    void deleteDeclaration(long idPatient);
}
