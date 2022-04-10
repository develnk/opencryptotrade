package com.opencryptotrade.cryptocurrencyservice.webapi.response;

import io.eventuate.EntityIdAndVersion;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class CreateCryptoCurrencyResponse {

    private EntityIdAndVersion cryptoCurrencyIdAndVersion;

}

