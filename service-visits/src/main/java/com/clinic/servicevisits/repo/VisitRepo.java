package com.clinic.servicevisits.repo;


import com.clinic.servicevisits.repo.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitRepo extends JpaRepository<Visit, Long> {
    @Query(value = "{CALL GET_ID_FROM_EMAIL(:email)}", nativeQuery = true)
    long getIdFromEmail(@Param("email") String email);

    List<Visit> getAllByDateVisit(LocalDate date);
}
