package com.homework.company.dao;

import lombok.Getter;

public enum Status {

    SUCCESS("success"),
    ADD("active department dependency"),
    AED("active employee dependency"),
    SQL_EXCEPTION("sql exception");

    @Getter
    private final String message;

    Status(String message) {
        this.message = message;
    }
}
