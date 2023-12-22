package com.ecommerce.ecommerce.Exception;

public record ApiErrorDto(
    String message,
    String backendMessage,
    String method,
    String url
){

}
