package com.opencryptotrade.common.reactive.model.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Employee extends com.opencryptotrade.common.model.entity.Employee {

    @Id
    @Field("_id")
    private String id;

}
