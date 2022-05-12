package com.opencryptotrade.cryptocurrencyservice.domain.model.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "crypto_currency_view")
@EntityListeners(AuditingEntityListener.class)
@Data
public class CryptoCurrencyView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.PUBLIC)
    private Integer id;

    @Column(name = "type", nullable = false, updatable = false)
    private String type;

    @Column(name = "symbol", nullable = false, updatable = false)
    private String symbol;

    @Column(name = "created_datetime", nullable = false, updatable = false)
    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Getter(AccessLevel.PUBLIC)
    private Date created;

    @Column(name = "updated_datetime", nullable = false)
    @LastModifiedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Getter(AccessLevel.PUBLIC)
    private Date updated;

}
