package com.smarthardwareshop.api.core.validators.annotations;

import com.smarthardwareshop.api.core.validators.interfaces.FieldValueExists;
import com.smarthardwareshop.api.core.validators.UniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Unique validator annotation.
 *
 * @see com.smarthardwareshop.api.core.validators.UniqueValidator
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@Documented
public @interface Unique {
    String message() default "The value must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends FieldValueExists> service();
    String serviceQualifier() default "";
    String fieldName();
}