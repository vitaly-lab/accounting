package com.accounting.exception;

public class DatabaseConnectivityException extends RuntimeException {
    public DatabaseConnectivityException(String message, Throwable cause) {
        super(message, cause);
    }
}
