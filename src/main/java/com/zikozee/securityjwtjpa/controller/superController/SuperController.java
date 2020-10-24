package com.zikozee.securityjwtjpa.controller.superController;


import com.zikozee.securityjwtjpa.Exceptions.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SuperController {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getLocalizedMessage());
    }
}
