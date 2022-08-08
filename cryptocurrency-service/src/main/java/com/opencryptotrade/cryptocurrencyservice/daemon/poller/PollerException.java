package com.opencryptotrade.cryptocurrencyservice.daemon.poller;

public class PollerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PollerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
