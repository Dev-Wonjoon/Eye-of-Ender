package com.eyeofender.utils;

import com.eyeofender.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    private ResponseUtil() {}

    public static ResponseEntity<GenericResponse> buildResponse(
            String message, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(new GenericResponse(message, httpStatus.value()));
    }

    public static ResponseEntity<GenericResponse> buildResponse(
            String message, HttpStatus httpStatus, Object data) {
        return ResponseEntity.status(httpStatus).body(new GenericResponse(message, httpStatus.value(), data));
    }

}
