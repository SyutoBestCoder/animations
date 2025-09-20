package com.syuto.animations.config.api;

public enum AnimationMode {
    VANILLA,  // keep this first thx - away
    EXHIBITION,
    ETB,
    SIGMA,
    DORTWARE,
    PLAIN,
    SPIN,
    AVATAR,
    SWONG,
    SWANG,
    SWANK,
    STYLES,
    NUDGE,
    PUNCH,
    JIGSAW,
    ROTATE,
    LUCKY,
    WIZZARD,
    LENNOX,
    SLIDE;



    public static AnimationMode fromJsonValue(String value) {
        try {
            return AnimationMode.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return VANILLA;
        }
    }
}