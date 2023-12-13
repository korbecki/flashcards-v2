package com.github.korbeckik.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueValueValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueValue {
    String message() default "Value exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String entityName();
    String fieldName();
}
