package com.softserve.itacademy.todolist.validator.constraint;

import com.softserve.itacademy.todolist.validator.TitleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TitleValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
public @interface Title {
    String message() default "Title minimum length is 3";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
