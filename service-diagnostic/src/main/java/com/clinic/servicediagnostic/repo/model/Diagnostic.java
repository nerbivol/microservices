package com.clinic.servicediagnostic.repo.model;

import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Table(name = "diagnostic")
public class Diagnostic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long idPatient;
    private String complains;
    private String conclusion;
    private String diagnose;
    private double price;
    private String additionalReview;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;

    public Diagnostic(long id, long idPatient, String complains, String conclusion, String diagnose, double price, String additionalReview, LocalDate dateCreated, LocalDate dateUpdated) {
        this.id = id;
        this.idPatient = idPatient;
        this.complains = complains;
        this.conclusion = conclusion;
        this.diagnose = diagnose;
        this.price = price;
        this.additionalReview = additionalReview;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public Diagnostic() { }

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

    public String getComplains() {
        return complains;
    }

    public void setComplains(String complains) {
        this.complains = complains;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAdditionalReview() {
        return additionalReview;
    }

    public void setAdditionalReview(String additionalReview) {
        this.additionalReview = additionalReview;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
