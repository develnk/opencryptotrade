package com.opencryptotrade.client.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterClient {

    private String email;
    private String firstName;
    private String lastName;
    private String password;

}
