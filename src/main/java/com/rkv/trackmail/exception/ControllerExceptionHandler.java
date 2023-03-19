package com.rkv.trackmail.exception;

import com.rkv.trackmail.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(CustomException ex) {
        return new ResponseEntity(new ErrorResponse("Invalid request: ", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GatewayException.class)
    @ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY)
    public ResponseEntity<ErrorResponse> handleServiceDependencyFailure(GatewayException ex) {
        return new ResponseEntity(new ErrorResponse("Failed to process request: ", ex.getMessage()), HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return new ResponseEntity(new ErrorResponse("Exception Occurred: ", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
