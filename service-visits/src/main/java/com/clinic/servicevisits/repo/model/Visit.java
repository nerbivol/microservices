package com.clinic.servicevisits.repo.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "visits")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long idPatient;
    private long idDoctor;
    private LocalDate dateVisit;
    private LocalTime timeVisit;

    public Visit(){ }

    public Visit(long idPatient, LocalDate dateVisit, LocalTime timeVisit) {
        this.idPatient = idPatient;
        this.dateVisit = dateVisit;
        this.timeVisit = timeVisit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(long idPatient) {
        this.idPatient = idPatient;
    }

    public LocalDate getDateVisit() {
        return dateVisit;
    }


    public void setDateVisit(LocalDate dateVisit) {
        this.dateVisit = dateVisit;
    }

    public LocalTime getTimeVisit() {
        return timeVisit;
    }

    public void setTimeVisit(LocalTime timeVisit) {
        this.timeVisit = timeVisit;
    }
}
