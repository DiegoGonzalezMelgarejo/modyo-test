package com.diego.pokeapi.infraestructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;

@Getter
public class SourceApiServerException extends RuntimeException {
    private final HttpStatus statusCode;

    public SourceApiServerException(String message, HttpStatus statusCode, RestClientException cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }
}
