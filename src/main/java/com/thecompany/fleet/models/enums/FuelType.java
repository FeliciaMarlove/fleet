package com.soprasteria.fleet.models.enums;

public enum FuelType {
    DIESEL(1),
    GASOLINE(2),
    HYBRID_GASOLINE(2),
    HYBRID_DIESEL(1),
    FULL_ELECTRIC(3);
    private int code;
    FuelType(int code) {
        this.code = code;
    }
    public int getCode() {
        return this.code;
    }
}
