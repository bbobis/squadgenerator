package com.benbobis.squadgenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class PlayerDataRetrievalException extends Exception {

    public PlayerDataRetrievalException(String message) {
        super(message);
    }
}
