package com.smarthardwareshop.api.users.enums;

/**
 * Represents a user role.
 */
public enum Role {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
