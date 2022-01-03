package com.clinic.servicevisits.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VisitDto {
    long idPatient;
    String date;
    String time;
}
