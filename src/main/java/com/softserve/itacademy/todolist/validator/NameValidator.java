package com.softserve.itacademy.todolist.validator;

import com.softserve.itacademy.todolist.validator.constraint.Name;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name == null) {
            return true;
        }
        return name.matches("[A-Z][a-z]+");
    }
}
