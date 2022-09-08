package com.softserve.itacademy.todolist.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    Long id;
    String firstName;
    String lastName;
    String email;
    String role;
}
