package com.smarthardwareshop.api.orders.enums;

/**
 * Order status.
 */
public enum OrderStatus {
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
