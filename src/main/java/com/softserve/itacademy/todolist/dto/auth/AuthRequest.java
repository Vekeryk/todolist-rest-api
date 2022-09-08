package com.softserve.itacademy.todolist.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AuthRequest {
    @NotBlank(message = "Email cannot be empty")
    String email;

    @NotBlank(message = "Password cannot be empty")
    String password;
}
