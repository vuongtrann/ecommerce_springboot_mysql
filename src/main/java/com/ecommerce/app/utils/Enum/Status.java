package com.ecommerce.app.utils.Enum;

public enum Status {
    ACTIVE(0),
    INACTIVE(1),
    DELETED(2);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Status fromValue(Integer value) {
        if (value == null) return null;

        for (Status status : Status.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Status value: " + value);
    }
}
