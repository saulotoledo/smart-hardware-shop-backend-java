package com.smarthardwareshop.api.core.validators.interfaces;

/**
 * Interface for the verification of a value for a given field. It should be implemented by services
 * to verify if there is an entry with the informed value for the informed field.
 *
 * @see com.smarthardwareshop.api.core.validators.UniqueValidator
 */
public interface FieldValueExists {

    /**
     * Checks whether or not a given value exists for a given field.
     * 
     * @param value The value to check for.
     * @param fieldName The name of the field for which to check if the value exists.
     * @return True if the value exists for the field, false otherwise.
     * @throws UnsupportedOperationException If the informed attribute is not unique.
     */
    boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException;
}
