package io.planet.api;

import lombok.Getter;

public class ApiException extends Exception{

    @Getter
    private int code;

    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }

}
