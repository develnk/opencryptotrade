package com.opencryptotrade.common.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.io.Serializable;

@Data
public class ProtectedUser implements Serializable {

    private String id;
    private String username;
    private String password;
    private String salt;

}
