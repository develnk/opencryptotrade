package com.opencryptotrade.cryptocurrencyservice.controller;

import com.opencryptotrade.common.validator.ErrorResponse;
import com.opencryptotrade.cryptocurrencyservice.domain.commands.CreateCryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.commands.UpdateCryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.exceptions.CryptoCurrencyDuplicateException;
import com.opencryptotrade.cryptocurrencyservice.service.CryptoCurrencyService;
import com.opencryptotrade.cryptocurrencyservice.webapi.request.CreateCryptoCurrencyRequest;
import com.opencryptotrade.cryptocurrencyservice.webapi.request.UpdateCryptoCurrencyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/cryptocurrency", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class CryptoCurrencyController {

    private final ReactorCommandGateway reactiveCommandGateway;
    private final ReactorQueryGateway reactiveQueryGateway;
    private final CryptoCurrencyService cryptoCurrencyService;

    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Mono<UUID> createCryptoCurrency(@Valid @RequestBody CreateCryptoCurrencyRequest request) {
        // Checking aggregate invariants before saving it to DB.
        cryptoCurrencyService.checkCryptoCurrencyExist(request.getSymbol(), request.getType()).subscribe(r -> {
            if (r) {
                throw new CryptoCurrencyDuplicateException("This aggregate already exist!");
            }
        });

        return reactiveCommandGateway.send(new CreateCryptoCurrencyCommand(UUID.randomUUID(), request.getType(), request.getSymbol(), request.getSettings()));
    }

    @PreAuthorize("hasAuthority('ROLE_SUPER')")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Mono<Void> updateCryptoCurrency(@Valid @RequestBody UpdateCryptoCurrencyRequest request) {
       return reactiveCommandGateway.send(new UpdateCryptoCurrencyCommand(request.getId(), request.getSymbol(), request.getSettings()));
    }

//    @ExceptionHandler({
//            EntityNotFoundException.class,
//    })
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handleTwilioException(EntityNotFoundException e) {
//        return new ErrorResponse(e.getMessage());
//    }

    @ExceptionHandler(CryptoCurrencyDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleCommonCryptoCurrency(RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }
}
