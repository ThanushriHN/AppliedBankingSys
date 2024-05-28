package com.abs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    CREATE("create"),
    DELETE("delete"),
    READ("read"),
    UPDATE("update");
    

    @Getter
    private final String permission;
}
