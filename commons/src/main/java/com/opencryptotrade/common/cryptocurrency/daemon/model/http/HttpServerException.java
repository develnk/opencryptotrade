package com.opencryptotrade.common.cryptocurrency.daemon.model.http;

public class HttpServerException extends HttpException {

    private static final long serialVersionUID = 1L;

    private final int status;
    private final String reason;

    public HttpServerException(int status, String reason) {
        super(reason);
        this.status = status;
        this.reason = reason;
    }

    public int status() {
        return status;
    }

    public String reason() {
        return reason;
    }
}
