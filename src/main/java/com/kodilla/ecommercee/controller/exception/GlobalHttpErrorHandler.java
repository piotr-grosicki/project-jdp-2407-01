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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException exception) {
        return new ResponseEntity<>("Order with this id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Object> handleCartNotFoundException(CartNotFoundException exception) {
        return new ResponseEntity<>("Cart with this id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyInCartException.class)
    public ResponseEntity<Object> handleProductAlredyInCartException(ProductAlreadyInCartException exception) {
        return new ResponseEntity<>("The product is already in the cart", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductDoesNotBelongToCartException.class)
    public ResponseEntity<Object> handleProductDoesNotBelongToCartException(ProductDoesNotBelongToCartException exception) {
        return new ResponseEntity<>("The product does not belong to the cart", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<Object> handleEmptyCartException(EmptyCartException exception) {
        return new ResponseEntity<>("Cart is empty", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CartWithOrderAlreadyException.class)
    public ResponseEntity<Object> handleCartWithOrderAlreadyException(CartWithOrderAlreadyException exception) {
        return new ResponseEntity<>("Order for this cart already exists", HttpStatus.CONFLICT);
    }
}

