package com.eyeofender.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GenericResponse {
    private String message;
    private int status;
    private Object data;

    public GenericResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public GenericResponse(String message, int status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
