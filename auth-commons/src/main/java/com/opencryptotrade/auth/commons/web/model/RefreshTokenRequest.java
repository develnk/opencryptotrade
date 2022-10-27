package com.opencryptotrade.auth.commons.web.model;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequest {

    @NotBlank
    private String refreshToken;

}
