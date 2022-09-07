package com.softserve.itacademy.todolist.dto.error;

import lombok.Value;

@Value
public class SimpleErrorResponse<T> {
    T error;
}
