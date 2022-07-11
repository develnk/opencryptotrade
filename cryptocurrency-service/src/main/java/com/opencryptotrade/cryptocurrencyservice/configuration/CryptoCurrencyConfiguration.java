package com.opencryptotrade.cryptocurrencyservice.configuration;

import org.springframework.context.annotation.*;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Configuration
@ComponentScan({"com.opencryptotrade"})
@EnableR2dbcAuditing
public class CryptoCurrencyConfiguration {



}
