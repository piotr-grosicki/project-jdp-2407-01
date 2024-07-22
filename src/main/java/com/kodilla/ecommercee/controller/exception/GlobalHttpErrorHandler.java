package com.kodilla.ecommercee.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException exception) {
        return new ResponseEntity<>("Product with this id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<Object> handleGroupNotFoundException(GroupNotFoundException exception) {
        return new ResponseEntity<>("Group with this id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<Object> handleProductAlreadyExistsException(ProductAlreadyExistsException exception) {
        return new ResponseEntity<>("Product with this name already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GroupAlreadyExistsException.class)
    public ResponseEntity<Object> handleGroupAlreadyExistsException(GroupAlreadyExistsException exception) {
        return new ResponseEntity<>("Group with this name already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>("User with this id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        return new ResponseEntity<>("User with this id already exist", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<Object> handleUserBlockedException(UserBlockedException exception) {
        return new ResponseEntity<>("User is blocked", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(GenerateRandomKeyException.class)
    public ResponseEntity<Object> handleGenerateRandomKeyException(GenerateRandomKeyException exception) {
        return new ResponseEntity<>("User validation not passed", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException exception) {
        return new ResponseEntity<>("User validation not passed", HttpStatus.CONFLICT);
    }
}
