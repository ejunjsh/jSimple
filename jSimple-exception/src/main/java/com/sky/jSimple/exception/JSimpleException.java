package com.sky.jSimple.exception;

public class JSimpleException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -276013798433878342L;

    public JSimpleException(Throwable e) {

        super(e);
    }

    public JSimpleException(String message) {
        super(message);
    }

}
