package com.kt.smartfarm.lamplog.models;

public enum LOG_RES_TYPE {
    NO_ERROR(""),
    S("System Error"),
    E("Business Error"),
    I("Information"),
    W("NOTIFY 의 경우 warning");
    public String value;
    LOG_RES_TYPE(String value) {
        this.value = value;
    }
    public String toString() {
        if( this == NO_ERROR ) {
            return "";
        }
        return this.name();
    }
}
