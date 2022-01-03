package com.clinic.servicevisits.service;

import com.clinic.servicevisits.repo.model.Visit;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface VisitService {
    List<Visit> getAllVisits();
    Visit getVisitById(long id);
    List<Visit> getVisitToday();
    long createVisit(long idPatient, LocalDate dateVisit, LocalTime timeVisit);
    void deleteVisit(long id);
}
