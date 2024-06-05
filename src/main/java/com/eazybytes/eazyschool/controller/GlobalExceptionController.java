package com.eazybytes.eazyschool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice(annotations = {Controller.class})   //Solo intercepta errores para las clases decoradas con @Controller.
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex){

        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");
        errorPage.addObject("errormsg", ex.getMessage());

        return errorPage;
    }
}
