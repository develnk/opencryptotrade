package com.opencryptotrade.common.reactive.model.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Customer extends com.opencryptotrade.common.model.entity.Customer {

    @Id
    @Field("_id")
    private String id;

}
