package com.opencryptotrade.cryptocurrencyservice.exceptions;

import java.io.Serial;

public class CryptoCurrencyDuplicateException extends  RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CryptoCurrencyDuplicateException(String message) {
        super(message);
    }
}
