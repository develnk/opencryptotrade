package com.opencryptotrade.common.model;

import io.eventuate.common.eventuate.local.BinLogEvent;
import io.eventuate.common.eventuate.local.BinlogFileOffset;
import lombok.*;
import java.util.Optional;

@Data
public class PublishedEvent implements BinLogEvent {

    private String eventId;
    private String entityId;
    private String entityType;
    private String eventData;
    private String eventType;

    @Getter(value = AccessLevel.NONE)
    private BinlogFileOffset binlogFileOffset;

    private Optional<String> metadata;

    @Override
    public Optional<BinlogFileOffset> getBinlogFileOffset() {
        return Optional.ofNullable(binlogFileOffset);
    }
}
