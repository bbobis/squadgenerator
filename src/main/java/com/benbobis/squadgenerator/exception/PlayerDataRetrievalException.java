package com.benbobis.squadgenerator.exception;

public class PlayerDataRetrievalException extends Exception {

    private static final String DEFAULT_ERROR_MESSAGE = "An error has occurred while retrieving player data.";

    public PlayerDataRetrievalException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

    public PlayerDataRetrievalException(String message) {
        super(message);
    }
}
