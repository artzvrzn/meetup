package org.modsen.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.modsen.controller.advice.error.ErrorResponse;
import org.modsen.controller.advice.error.MultipleResponseError;
import org.modsen.controller.advice.error.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse illegalArgumentHandler(IllegalArgumentException exc) {
        log.error(exc.getMessage(), exc);
        return new ErrorResponse(exc.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MultipleResponseError methodArgumentNotValidHandler(MethodArgumentNotValidException exc) {
        log.error(exc.getBindingResult().getFieldErrors().toString());
        List<Violation> violations = exc.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new MultipleResponseError("structured_error", violations);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse httpMessageConversionHandler(HttpMessageNotReadableException exc) {
        log.error(exc.getMessage(), exc);
        return new ErrorResponse("Request contains invalid parameters");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse exceptionHandler(Exception exc) {
        log.error(exc.getMessage(), exc);
        return new ErrorResponse("Server failed to process the request");
    }

}
