package com.africa.semicolon.emailService.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserAccount {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
