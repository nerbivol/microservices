package com.clinic.servicevisits.api;

import com.clinic.servicevisits.api.dto.VisitDto;
import com.clinic.servicevisits.repo.model.Visit;
import com.clinic.servicevisits.service.impl.VisitServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitController {
    private final VisitServiceImpl visitServiceImpl;

    @GetMapping
    public ResponseEntity<List<Visit>> index(){
        return ResponseEntity.ok(visitServiceImpl.getAllVisits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visit> showById(@PathVariable long id){
        try {
            final Visit visit = visitServiceImpl.getVisitById(id);
            return ResponseEntity.ok(visit);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/today")
    public ResponseEntity<List<Visit>> showToday(){
        final List<Visit> visit = visitServiceImpl.getVisitToday();
        return ResponseEntity.ok(visit);
    }

    @PostMapping
    public ResponseEntity<Visit> create(@RequestBody VisitDto visit){
        long idPatient = visit.getIdPatient();
        LocalDate dateVisit = LocalDate.parse(visit.getDate());
        LocalTime timeVisit = LocalTime.parse(visit.getTime());
        final long visitId = visitServiceImpl.createVisit(idPatient, dateVisit, timeVisit);
        final String visitUri = String.format("/visits/%d", visitId);

        return ResponseEntity.created(URI.create(visitUri)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
        visitServiceImpl.deleteVisit(id);
        return ResponseEntity.noContent().build();
    }

}
