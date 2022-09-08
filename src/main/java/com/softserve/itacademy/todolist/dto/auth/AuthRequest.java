package com.softserve.itacademy.todolist.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AuthRequest {
    @NotBlank
    String email;
    @NotBlank
    String password;
}
