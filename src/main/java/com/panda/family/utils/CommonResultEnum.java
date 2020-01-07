package com.panda.family.utils;

public enum CommonResultEnum {

    SUCCESS(1), FAILED(0);

    private int value;

    CommonResultEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
