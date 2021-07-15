package ru.geekbrains.DreamLandStore.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler({NullPointerException.class,IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String notFoundException(RuntimeException e){
        log.error("Error! " + e.getMessage(), e);
        return "Not found!!";
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String notFoundException(UsernameNotFoundException e){
        log.error("Not found!! " + e.getMessage(), e);
        return "Not found!!" + e.getMessage();
    }

    @ExceptionHandler(ClassCastException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String nClassCastException(ClassCastException e){
        log.error("Not found!! " + e.getMessage(), e);
        return "Not found!!" + e.getMessage();
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String allException(Throwable e){
        log.error("Sorry some Error" + e.getMessage(), e);
        return "Sorry some Error";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String allException(DataIntegrityViolationException e){
        log.error("Sorry some Error" + e.getMessage(), e);
        return "Sorry some Error";
    }

}
