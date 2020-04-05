package com.kt.smartfarm.lamplog.models;

public enum LOG_SECURITY_TYPE {
    ACCESS, PRCS, MNGT, AUTH;
    public String toString() {
        return this.name();
    }
}
