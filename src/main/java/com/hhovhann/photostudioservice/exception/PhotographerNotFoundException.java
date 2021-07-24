package com.hhovhann.photostudioservice.exception;

public class PhotographerNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 4285341894354118776L;

    public PhotographerNotFoundException() {
        super();
    }

    public PhotographerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhotographerNotFoundException(String message) {
        super(message);
    }

    public PhotographerNotFoundException(Throwable cause) {
        super(cause);
    }
}