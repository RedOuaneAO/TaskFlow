package com.redone.taskflow.handler.customExceptions;

public class DemandNotFoundException extends RuntimeException{

    public DemandNotFoundException(String message) {
        super(message);
    }
}
