package com.clinic.servicediagnostic.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class UserDto {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
}
