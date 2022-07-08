package com.opencryptotrade.cryptocurrencyservice.domain.projections;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
@Table(name = "crypto_currency_view")
public class CryptoCurrencyView {

    @Id
    @Getter(AccessLevel.PUBLIC)
    private Integer id;
    private String type;
    private String symbol;
    private String entityId;

    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Getter(AccessLevel.PUBLIC)
    private Date created;

    @LastModifiedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Getter(AccessLevel.PUBLIC)
    private Date updated;

}
