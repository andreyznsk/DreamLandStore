package ru.geekbrains.DreamLandStore.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice

public class ExceptionAdvice {

    @ExceptionHandler({NullPointerException.class,IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String notFoundException(RuntimeException e){
        System.out.println("Error! " + e.getMessage());
        e.printStackTrace();
        return "Not found!!";
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String notFoundException(UsernameNotFoundException e){
        System.out.println("Not found!! " + e.getMessage());
        e.printStackTrace();
        return "Not found!!" + e.getMessage();
    }

    @ExceptionHandler(ClassCastException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String nClassCastException(ClassCastException e){
        System.out.println("Not found!! " + e.getMessage());
        e.printStackTrace();
        return "Not found!!" + e.getMessage();
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String allException(Throwable e){
        System.out.println("Sorry some Error" + e.getMessage());
        e.printStackTrace();
        return "Sorry some Error";
    }

}
