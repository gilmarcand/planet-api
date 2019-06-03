package io.planet.api;

public class ApiException extends Exception{

    private int code;

    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return super.getMessage();
    }
}
