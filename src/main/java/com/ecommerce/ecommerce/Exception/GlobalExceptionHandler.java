package com.ecommerce.ecommerce.Exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import jakarta.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDto> handlerGeneralException(Exception exception, HttpServletRequest request){
        exception.printStackTrace();
        if(exception instanceof HttpClientErrorException){
            return this.handleHttpClientErrorException((HttpClientErrorException) exception, request);
        }else if(exception instanceof AccessDeniedException){
            return this.handleAccessDeniedException((AccessDeniedException) exception, request);
        }else if(exception instanceof AuthenticationCredentialsNotFoundException){
            return this.handleAuthenticationCredentialsNotFoundException((AuthenticationCredentialsNotFoundException) exception, request);
        }else {
            return this.handleGenericException(exception, request);
        }
    }

    private ResponseEntity<ApiErrorDto> handleGenericException(Exception exception, HttpServletRequest request) {
               
        ApiErrorDto apiErrorDto = new ApiErrorDto("Unexpected error, try again", 
                                                    exception.getMessage(), 
                                                    request.getMethod(), 
                                                    request.getRequestURL().toString());          
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiErrorDto);
    }


    private ResponseEntity<ApiErrorDto> handleAuthenticationCredentialsNotFoundException(
            AuthenticationCredentialsNotFoundException exception, HttpServletRequest request) {
                       
        ApiErrorDto apiErrorDto = new ApiErrorDto("Acces to this resource not allowed", 
                                                    exception.getMessage(), 
                                                    request.getMethod(), 
                                                    request.getRequestURL().toString());          
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiErrorDto);
    }

    private ResponseEntity<ApiErrorDto> handleAccessDeniedException(Exception exception,
            HttpServletRequest request) {
        
        ApiErrorDto apiErrorDto = new ApiErrorDto("Acces to this resource not allowed", 
                                                    exception.getMessage(), 
                                                    request.getMethod(), 
                                                    request.getRequestURL().toString());          
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiErrorDto);
    }


    private ResponseEntity<ApiErrorDto> handleHttpClientErrorException(HttpClientErrorException exception,
            HttpServletRequest request) {
        
        String message = null;

        if(exception instanceof HttpClientErrorException.Forbidden){
            message = "Acces to this resource not allowed";
        }else if (exception instanceof HttpClientErrorException.Unauthorized){
            message = "Login not found";
        }else if (exception instanceof HttpClientErrorException.NotFound){
            message = "Resource not found";
        }else if (exception instanceof HttpClientErrorException.Conflict){
            message = "Conflict to proces";
        }else{
            message = "Unexpected error";
        }

        ApiErrorDto apiErrorDto = new ApiErrorDto(message, 
                                                    exception.getMessage(), 
                                                    request.getMethod(), 
                                                    request.getRequestURL().toString());          
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiErrorDto);
    }
    
}
