package com.clinic.servicevisits.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DeclarationDto {
    long idDoctor;
    long idPatient;
}
