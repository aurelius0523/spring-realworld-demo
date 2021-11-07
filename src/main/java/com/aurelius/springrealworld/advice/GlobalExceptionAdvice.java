package com.aurelius.springrealworld.advice;

import com.aurelius.springrealworld.exception.BusinessValidationException;
import com.aurelius.springrealworld.facade.model.ApiErrorModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody
    ApiErrorModel handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Request validation exception occurred", ex);
        List<String> validationErrorList = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> String.format("%s %s", ((FieldError) objectError).getField(), objectError.getDefaultMessage()))
                .collect(Collectors.toList());

        return ApiErrorModel.builder()
                .body(validationErrorList)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessValidationException.class)
    public @ResponseBody
    ApiErrorModel handleValidationException(BusinessValidationException ex) {
        log.error("Business Validation exception occurred", ex);
        return ApiErrorModel.builder()
                .body(List.of(ex.getMessage()))
                .build();
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class, Exception.class})
    public @ResponseBody
    ApiErrorModel handleGeneralException(Exception ex) {
        log.error("Unhandled Exception occurred", ex);
        return ApiErrorModel.builder()
                .body(List.of(ex.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UsernameNotFoundException.class})
    public @ResponseBody
    ApiErrorModel handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ApiErrorModel.builder()
                .body(List.of(ex.getMessage()))
                .build();
    }
}
