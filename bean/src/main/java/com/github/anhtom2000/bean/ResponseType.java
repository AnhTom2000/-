package com.github.anhtom2000.bean;

public enum ResponseType {
    SUCCESS(200),
    NOTFOUND(404),
    REGISTER_FAILED(502),
    LOGIN_ERROR(503),
    SERVER_ERROR(500)
    ;
    private int code;

    ResponseType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ResponseType{" +
                "code=" + code +
                '}';
    }
}
