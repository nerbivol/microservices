package com.clinic.servicedeclaration.repo;

import com.clinic.servicedeclaration.repo.model.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeclarationRepo extends JpaRepository<Declaration, Long> {
    List<Declaration> findAllByIdDoctor(Long idDoctor);
    Optional<Declaration> findByIdPatient(long idPatient);
}
