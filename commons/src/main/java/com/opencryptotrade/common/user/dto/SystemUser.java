package com.opencryptotrade.common.user.dto;

import java.io.Serializable;

public interface SystemUser extends Serializable {

    String getFirstName();
    String getLastName();
    String getFullName();

}
