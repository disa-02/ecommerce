package com.ecommerce.ecommerce.Exception;

public class ApiErrorException extends RuntimeException {

    public ApiErrorException(String string) {
        super(string);
    }
}
