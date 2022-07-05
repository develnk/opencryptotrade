package com.opencryptotrade.common.cryptocurrency.daemon.model.http;

public class HttpException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HttpException(Throwable cause) {
        super(cause);
    }

    public HttpException(String message) {
        super(message);
    }
}
