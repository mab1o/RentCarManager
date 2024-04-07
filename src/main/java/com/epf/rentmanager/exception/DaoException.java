package com.epf.rentmanager.exception;

public class DaoException extends Exception {
    public DaoException() {
        super();
    }
    public DaoException(String s) {
        super("\nDaoException: " + s);
    }

    public DaoException(String message, Throwable cause) {
        super("\nDaoException: " + message, cause);
    }

}
