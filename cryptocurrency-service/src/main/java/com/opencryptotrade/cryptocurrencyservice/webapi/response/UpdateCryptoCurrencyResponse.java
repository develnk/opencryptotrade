package com.opencryptotrade.cryptocurrencyservice.webapi.response;

import io.eventuate.EntityIdAndVersion;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCryptoCurrencyResponse {

    private EntityIdAndVersion cryptoCurrencyIdAndVersion;

}
