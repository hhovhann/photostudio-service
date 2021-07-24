package com.hhovhann.photostudioservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 4285341894354118776L;

    public OrderNotFoundException() {
        super();
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(Throwable cause) {
        super(cause);
    }
}