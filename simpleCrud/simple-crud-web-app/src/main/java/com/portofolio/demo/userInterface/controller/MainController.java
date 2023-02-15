package com.portofolio.demo.userInterface.controller;

import com.portofolio.demo.models.json.ErrorMessage;
import com.portofolio.demo.shared.errors.BusinessException;
import com.portofolio.demo.shared.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MainController {

    //TODO create tests for each exception handler

    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBusinessException(BusinessException exception) {
        return ErrorMessage.createErrorWithMessage(exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        ObjectError error = exception.getAllErrors().get(0);

        return ErrorMessage.createErrorWithMessage(error.getDefaultMessage());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ErrorMessage.createErrorWithMessage(exception.getMessage());
    }
}
