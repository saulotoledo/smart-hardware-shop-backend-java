package com.smarthardwareshop.api.core.validators;

import com.smarthardwareshop.api.core.validators.annotations.Unique;
import com.smarthardwareshop.api.core.validators.interfaces.FieldValueExists;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Constraint validator for unique values.
 * It requires the configuration spring.jpa.properties.javax.persistence.validation.mode=none
 * Adapted from https://codingexplained.com/coding/java/hibernate/unique-field-validation-using-hibernate-spring
 */
@RequiredArgsConstructor
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    /**
     * The application context.
     */
    private final ApplicationContext applicationContext;

    /**
     * A service implementing the FieldValueExists interface.
     */
    private FieldValueExists service;

    /**
     * The name of the field to verify.
     */
    private String fieldName;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(Unique unique) {
        Class<? extends FieldValueExists> clazz = unique.service();
        this.fieldName = unique.fieldName();
        String serviceQualifier = unique.serviceQualifier();

        if (!serviceQualifier.equals("")) {
            this.service = this.applicationContext.getBean(serviceQualifier, clazz);
        } else {
            this.service = this.applicationContext.getBean(clazz);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return !this.service.fieldValueExists(o, this.fieldName);
    }
}
