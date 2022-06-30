package com.opencryptotrade.cryptocurrencyservice.daemon;

public interface DaemonProvider {

    Boolean status();
    Integer currentHeight();

}
