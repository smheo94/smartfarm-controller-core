package com.kt.smartfarm.lamplog.models;

public enum LOG_SECURITY_EVENT {
    LOGIN, LOGOUT, CREATE, READ, UPDATE, EXPORT, DELETE, IPIN, EMAIL, PHONE;
    public String toString() {
        return this.name();
    }
}
