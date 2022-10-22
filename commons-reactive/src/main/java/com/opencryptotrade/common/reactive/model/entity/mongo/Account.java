package com.opencryptotrade.common.reactive.model.entity.mongo;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

@Document
public class Account extends com.opencryptotrade.common.model.entity.Account {

    @Id
    @Field("_id")
    private String id;

    @ToString.Exclude
    @Field("_customer")
    @DBRef
    private Customer customer;

    @ToString.Exclude
    @Field("_manager")
    @DBRef
    private Employee manager;

}
