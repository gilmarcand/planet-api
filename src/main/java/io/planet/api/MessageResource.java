package io.planet.api;

import lombok.Getter;

@Getter
public class MessageResource {

    public static final int ERROR = 0;
    public static final int INFO = 1;

    private String type;
    private int code;
    private String message;

    public MessageResource(int code, String message){
        this.code = code;
        switch(code){
            case ERROR:
                this.type = "error";
                break;
            case INFO:
                this.type = "info";
                break;
            default:
                this.type = "unknown";
                break;
        }
        this.message = message;
    }

}