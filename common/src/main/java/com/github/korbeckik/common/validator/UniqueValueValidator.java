package com.github.korbeckik.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
@RequiredArgsConstructor
public class UniqueValueValidator implements ConstraintValidator<UniqueValue, String> {

    private String entity;
    private String field;

    private final JdbcTemplate jdbcTemplate;
//    private final R2dbcEntityTemplate jdbcTemplate;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String query = String.format("""
                SELECT CASE WHEN EXISTS (
                    SELECT *
                    FROM %s
                    WHERE %s = '%s'
                )
                                THEN CAST(1 AS BIT)
                            ELSE CAST(0 AS BIT) END
                """, entity, field, s);
        return Boolean.FALSE.equals(jdbcTemplate.queryForObject(query, Boolean.class));
    }

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.entity = constraintAnnotation.entityName();
        this.field = constraintAnnotation.fieldName();
    }
}
