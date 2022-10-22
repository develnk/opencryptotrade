package com.opencryptotrade.common.reactive.model.entity.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "protected_user")
public class ProtectedUser extends com.opencryptotrade.common.model.entity.ProtectedUser {

    @Id
    @Field("_id")
    private String id;

}
