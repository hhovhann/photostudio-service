package com.hhovhann.photostudioservice.exception;

public class PhotoStudioValidationException extends RuntimeException {
    private static final long serialVersionUID = -9038935358708640825L;

    public PhotoStudioValidationException() {
        super();
    }

    public PhotoStudioValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhotoStudioValidationException(String message) {
        super(message);
    }

    public PhotoStudioValidationException(Throwable cause) {
        super(cause);
    }
}