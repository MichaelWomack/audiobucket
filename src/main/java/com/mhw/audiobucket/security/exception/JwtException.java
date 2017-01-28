package com.mhw.audiobucket.security.exception;

/**
 * Created by mxw4182 on 12/26/16.
 */
public class JwtException extends Exception {

    public JwtException(String message) {
        super(message);
    }

    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtException(Throwable cause) {
        super(cause);
    }
}
