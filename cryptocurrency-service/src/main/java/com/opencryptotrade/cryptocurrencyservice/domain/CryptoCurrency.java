package com.opencryptotrade.cryptocurrencyservice.domain;

import com.opencryptotrade.cryptocurrencyservice.domain.command.CreateCryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.command.CryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.command.UpdateCryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.command.UpdateCryptoCurrencyDaemonStatusCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonStatus;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyType;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.cryptocurrencyservice.events.CryptoCurrencyCreatedEvent;
import com.opencryptotrade.cryptocurrencyservice.events.CryptoCurrencyUpdatedEvent;
import com.opencryptotrade.cryptocurrencyservice.events.CryptocurrencyUpdatedStatusEvent;
import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import lombok.Getter;
import java.util.List;

@SuppressWarnings("all")
@Getter
public class CryptoCurrency extends ReflectiveMutableCommandProcessingAggregate<CryptoCurrency, CryptoCurrencyCommand> {

    private CryptoCurrencyType type;
    private String symbol;
    private CryptoCurrencyDaemonSettings settings;
    private CryptoCurrencyDaemonStatus status;

    public List<Event> process(CreateCryptoCurrencyCommand cmd) {
        return EventUtil.events(new CryptoCurrencyCreatedEvent(cmd.getSymbol(), cmd.getType(), cmd.getCryptoCurrencyDaemonSettings()));
    }

    public void apply(CryptoCurrencyCreatedEvent event) {
        this.type = event.getType();
        this.symbol = event.getSymbol();
        this.settings = event.getSettings();
    }

    public List<Event> process(UpdateCryptoCurrencyCommand cmd) {
        return EventUtil.events(new CryptoCurrencyUpdatedEvent(cmd.getSymbol(), cmd.getCryptoCurrencyDaemonSettings()));
    }

    public void apply(CryptoCurrencyUpdatedEvent event) {
        this.symbol = event.getSymbol();
        this.settings = event.getSettings();
    }

    public List<Event> process(UpdateCryptoCurrencyDaemonStatusCommand cmd) {
        return EventUtil.events(new CryptocurrencyUpdatedStatusEvent(cmd.getStatus()));
    }

    public void apply(CryptocurrencyUpdatedStatusEvent event) {
        this.status = event.getStatus();
    }

}
