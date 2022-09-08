package com.softserve.itacademy.todolist.validator.constraint;

import com.softserve.itacademy.todolist.validator.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
public @interface Password {
    String message() default "Minimum password length is 6 with digits and letters in upper and lower case";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
