package com.hhovhann.photostudioservice.exception.handler;

import com.hhovhann.photostudioservice.exception.model.ErrorResponse;
import com.hhovhann.photostudioservice.exception.OrderNotFoundException;
import com.hhovhann.photostudioservice.exception.PhotoStudioValidationException;
import com.hhovhann.photostudioservice.exception.PhotographerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String NOT_FOUND = "NOT_FOUND";
    private static final String BAD_REQUEST = "BAD_REQUEST";

    private ResponseEntity<Object> generateResponseEntity(RuntimeException ex, String badRequest, HttpStatus badRequest2) {
        return new ResponseEntity<>(new ErrorResponse(badRequest, List.of(ex.getLocalizedMessage())), badRequest2);
    }

    @ExceptionHandler(PhotoStudioValidationException.class)
    protected ResponseEntity<Object> handleValidationException(RuntimeException ex, WebRequest request) {
        return generateResponseEntity(ex, BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {OrderNotFoundException.class, PhotographerNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(RuntimeException ex, WebRequest request) {
        return generateResponseEntity(ex, NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
