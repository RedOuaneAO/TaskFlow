package com.redone.taskflow.handler;

import com.redone.taskflow.handler.customExceptions.DemandNotFoundException;
import com.redone.taskflow.handler.customExceptions.TaskNotFoundException;
import com.redone.taskflow.handler.customExceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class Exception_handler {

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String,Object>> handleRuntimeException(RuntimeException ex) {
        Map<String,Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status","error");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,Object>> handleValidationExceptions(MethodArgumentNotValidException ex, BindingResult bindingResult) {
        Map<String,Object> response = new HashMap<>();
        String errors="" ;
        if(bindingResult.hasErrors()){
            for(ObjectError error:bindingResult.getAllErrors()){
                errors = error.getDefaultMessage();
            }
        }
        response.put("message",errors );
        response.put("status","error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({DemandNotFoundException.class, TaskNotFoundException.class , UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleNotFoundExceptions(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", "error");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
