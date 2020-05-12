package com.opencryptotrade.smtpservice.enums;

public enum Trigger {
    CLIENT_CREATE_NEW("Create new client", 1),
    CLIENT_RESTORE_PASSWORD("Restore client password", 2),
    CLIENT_UPDATE_PROFILE("Update client profile", 3),
    CLIENT_NOTICE_LOGIN("Notice client login", 4),
    ADMIN_NOTICE_LOGIN("Admin Notice login", 5);

    String value;
    Integer id;

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    Trigger(String value, Integer id) {
        this.value = value;
        this.id = id;
    }

}
