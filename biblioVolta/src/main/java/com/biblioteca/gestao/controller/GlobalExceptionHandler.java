package com.biblioteca.gestao.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(Exception ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", ex.getClass().getName());
        mav.addObject("message", ex.getMessage());
        mav.addObject("url", request.getRequestURL());

        StringBuilder sb = new StringBuilder();
        for (StackTraceElement el : ex.getStackTrace()) {
            sb.append(el.toString()).append("\n");
            if (sb.length() > 3000) break;
        }
        mav.addObject("stacktrace", sb.toString());

        Throwable cause = ex.getCause();
        if (cause != null) {
            mav.addObject("cause", cause.getClass().getName() + ": " + cause.getMessage());
        }

        return mav;
    }
}
