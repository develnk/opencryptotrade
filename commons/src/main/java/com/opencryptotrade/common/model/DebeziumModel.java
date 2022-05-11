package com.opencryptotrade.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DebeziumModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Schema schema;
    private Payload payload;


    @Data
    private static class Schema implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private String type;
        private Boolean optional;
        private String name;
    }

    @Data
    public static class Payload implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private PublishedEvent before;
        private PublishedEvent after;
        private DebeziumSource source;
        private String op;

        @JsonProperty(value = "ts_ms")
        private BigDecimal tsMs;
        private Object transaction;
    }

    @Data
    public static class DebeziumSource implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private String version;
        private String connector;
        private String name;

        @JsonProperty(value = "ts_ms")
        private BigDecimal tsMs;
        private String snapshot;
        private String db;
        private String sequence;
        private String schema;
        private String table;
        private BigInteger txId;
        private BigInteger lsn;
        private BigInteger xmin;

    }
}
