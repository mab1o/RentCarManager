package com.epf.rentmanager.exception;

public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }
    public ServiceException(String s) {
        super(s);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
