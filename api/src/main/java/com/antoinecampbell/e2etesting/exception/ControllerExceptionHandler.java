package com.antoinecampbell.e2etesting.exception;

import com.antoinecampbell.e2etesting.user.UserNameExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * Error handler for exceptions thrown from Controllers
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class,
            ServletRequestBindingException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintException(Exception e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .errorCode("ERROR_INVALID_REQUEST")
                .build();
    }

    @ExceptionHandler({UserNameExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUsernameExistException(Exception e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .errorCode("ERROR_USERNAME_EXISTS")
                .build();
    }
}
