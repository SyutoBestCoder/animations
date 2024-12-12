package com.syuto.animations.config.api;

public enum AnimationMode {
    EXHIBITION,
    SIGMA,
    VANILLA,
    PLAIN,
    SPIN;


    public static AnimationMode fromJsonValue(String value) {
        try {
            return AnimationMode.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return VANILLA;
        }
    }
}