package com.softserve.itacademy.todolist.validator;

import com.softserve.itacademy.todolist.validator.constraint.Title;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TitleValidator implements ConstraintValidator<Title, String> {
    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        if (title == null) {
            return true;
        }
        title = title.trim();
        return !title.isBlank() && title.length() > 2;
    }
}
