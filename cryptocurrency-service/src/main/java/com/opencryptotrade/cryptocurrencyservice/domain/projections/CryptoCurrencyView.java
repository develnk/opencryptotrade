package com.opencryptotrade.cryptocurrencyservice.domain.projections;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(name = "crypto_currency_view")
public class CryptoCurrencyView {

    @Id
    private Integer id;
    private String type;
    private String symbol;
    private String entityId;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

}
