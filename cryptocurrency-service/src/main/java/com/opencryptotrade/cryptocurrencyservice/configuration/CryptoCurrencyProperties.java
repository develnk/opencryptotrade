package com.opencryptotrade.cryptocurrencyservice.configuration;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "crypto-currency.command")
@Value
public class CryptoCurrencyProperties {
    private Integer snapshotTriggerThresholdCryptoCurrency = 5;
}
