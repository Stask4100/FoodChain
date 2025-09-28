package com.kornievstas.FoodChain.exception;

public class AlreadyDeadException extends RuntimeException {
    public AlreadyDeadException(String message) {
        super(message);
    }
}
