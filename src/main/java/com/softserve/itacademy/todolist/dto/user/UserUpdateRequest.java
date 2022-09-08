package com.softserve.itacademy.todolist.dto.user;

import com.softserve.itacademy.todolist.validator.constraint.Name;
import com.softserve.itacademy.todolist.validator.constraint.Password;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor

public class UserUpdateRequest {
    @Name
    String firstName;
    @Name
    String lastName;
    @Email
    String email;
    @Password
    String password;
}
