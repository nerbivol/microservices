package com.clinic.servicedeclaration.api;

import com.clinic.servicedeclaration.api.dto.DeclarationDto;
import com.clinic.servicedeclaration.repo.model.Declaration;
import com.clinic.servicedeclaration.service.impl.DeclarationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/declarations")
public class DeclarationController {

    final private DeclarationServiceImpl declarationServiceImpl;

    @Autowired
    public DeclarationController(DeclarationServiceImpl declarationServiceImpl) {
        this.declarationServiceImpl = declarationServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Declaration>> index(){
        final List<Declaration> declarations = declarationServiceImpl.GetAllDeclarations();
        return ResponseEntity.ok(declarations);
    }

    @GetMapping("/doctors/{idDoctor}")
    public ResponseEntity<List<Declaration>> showByDoctorId(@PathVariable long idDoctor){
        try {
            List<Declaration> declarations = declarationServiceImpl.GetByDoctorId(idDoctor);
            return ResponseEntity.ok(declarations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<Declaration> showByPatientId(@PathVariable long id){
        try {
            return ResponseEntity.ok(declarationServiceImpl.GetByPatient(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Declaration> create(@RequestBody DeclarationDto newDeclaration){
        final long doctor = newDeclaration.getIdDoctor();
        final long patient = newDeclaration.getIdPatient();
        final long decl = declarationServiceImpl.createDeclaration(patient, doctor);
        final String declarationUri = String.format("/declaration/patient/%d", decl);

        return ResponseEntity.created(URI.create(declarationUri)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        declarationServiceImpl.deleteDeclaration(id);
        return ResponseEntity.noContent().build();
    }
}
