package com.mhw.audiobucket.exceptions;

/**
 * Created by michaelwomack on 1/15/17.
 */
public class ApplicationConfigException extends Exception {

    public ApplicationConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationConfigException(Throwable cause) {
        super(cause);
    }

    public ApplicationConfigException(String message) {
        super(message);
    }
}
