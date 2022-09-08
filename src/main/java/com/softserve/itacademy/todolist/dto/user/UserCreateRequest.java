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
    @NotBlank(message = "First name cannot be empty")
    String firstName;

    @Name
    @NotBlank(message = "Last name cannot be empty")
    String lastName;

    @Email(message = "Email is invalid")
    @NotBlank(message = "Email cannot be empty")
    String email;

    @Password
    @NotBlank(message = "Password cannot be empty")
    String password;
}