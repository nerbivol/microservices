package com.clinic.servicediagnostic.repo;

import com.clinic.servicediagnostic.repo.model.Diagnostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticRepo extends JpaRepository<Diagnostic, Long> {
    List<Diagnostic> findAllByIdPatient(long id);
}
