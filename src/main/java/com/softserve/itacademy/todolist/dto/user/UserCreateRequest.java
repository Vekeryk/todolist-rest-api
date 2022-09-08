package com.softserve.itacademy.todolist.dto.user;

import com.softserve.itacademy.todolist.validator.constraint.Name;
import com.softserve.itacademy.todolist.validator.constraint.Password;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserCreateRequest {
    @Name
    @NotBlank
    String firstName;

    @Name
    @NotBlank
    String lastName;

    @Email
    @NotBlank
    String email;

    @Password
    @NotBlank
    String password;
}