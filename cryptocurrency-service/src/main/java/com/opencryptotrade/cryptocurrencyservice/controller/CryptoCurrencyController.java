package com.opencryptotrade.cryptocurrencyservice.controller;

import com.opencryptotrade.common.validator.ErrorResponse;
import com.opencryptotrade.cryptocurrencyservice.domain.CryptoCurrency;
import com.opencryptotrade.cryptocurrencyservice.exceptions.CryptoCurrencyDuplicateException;
import com.opencryptotrade.cryptocurrencyservice.service.CryptoCurrencyService;
import com.opencryptotrade.cryptocurrencyservice.webapi.request.CreateCryptoCurrencyRequest;
import com.opencryptotrade.cryptocurrencyservice.webapi.request.UpdateCryptoCurrencyRequest;
import com.opencryptotrade.cryptocurrencyservice.webapi.response.CreateCryptoCurrencyResponse;
import com.opencryptotrade.cryptocurrencyservice.webapi.response.UpdateCryptoCurrencyResponse;
import io.eventuate.EntityNotFoundException;
import io.eventuate.EntityWithIdAndVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/cryptocurrency", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CryptoCurrencyController {

    private final CryptoCurrencyService cryptoCurrencyService;

    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CreateCryptoCurrencyResponse createCryptoCurrency(@Valid @RequestBody CreateCryptoCurrencyRequest request) {
        EntityWithIdAndVersion<CryptoCurrency> entity = cryptoCurrencyService.createCryptoCurrency(request.getType(), request.getSymbol(), request.getSettings());
        return new CreateCryptoCurrencyResponse(entity.getEntityIdAndVersion());
    }

    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public UpdateCryptoCurrencyResponse updateCryptoCurrency(@Valid @RequestBody UpdateCryptoCurrencyRequest request) {
        EntityWithIdAndVersion<CryptoCurrency> entity = cryptoCurrencyService.updateCryptoCurrency(request.getEntityId(), request.getSymbol(), request.getSettings());
        return new UpdateCryptoCurrencyResponse(entity.getEntityIdAndVersion());
    }

    @ExceptionHandler({
            EntityNotFoundException.class,
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTwilioException(EntityNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(CryptoCurrencyDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCommonCryptoCurrency(RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }
}
