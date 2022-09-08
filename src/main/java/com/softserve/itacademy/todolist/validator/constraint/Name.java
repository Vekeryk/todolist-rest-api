package com.softserve.itacademy.todolist.validator.constraint;

import com.softserve.itacademy.todolist.validator.NameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
public @interface Name {
    String message() default "Your name must start with a capital followed by lowercase letters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
