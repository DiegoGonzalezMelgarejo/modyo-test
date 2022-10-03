package com.diego.pokeapi.infraestructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

@Getter
public class SourceApiClientException extends RuntimeException {
    // class could extend from RetryableException to implement retry attempts somewhere
    private final HttpStatus statusCode;

    public SourceApiClientException(String message, HttpStatus statusCode, HttpClientErrorException cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }
}
