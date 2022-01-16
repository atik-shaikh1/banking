package com.atik.banking.controller;

import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
 
import java.util.HashMap;
import java.util.Map;
 
@ControllerAdvice
public class ExceptionHandler {
 
    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Map<String,String> badRequest(Exception e){
        Map<String,String> response = new HashMap<>();
        response.put(HttpStatus.BAD_REQUEST.toString(), "Your input is invalid");

        return response;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    Map<String,String> invalidJsonInput(JsonParseException e){
        Map<String,String> response = new HashMap<>();
        response.put(HttpStatus.UNPROCESSABLE_ENTITY.toString(), "Your input is invalid");

        return response;
    }

}