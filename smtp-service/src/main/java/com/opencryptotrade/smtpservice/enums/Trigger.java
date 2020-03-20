package com.opencryptotrade.smtpservice.enums;

public enum Trigger {
    CLIENT_CREATE_NEW("Create new client"),
    CLIENT_RESTORE_PASSWORD("Restore client password"),
    CLIENT_UPDATE_PROFILE("Update client profile"),
    CLIENT_NOTICE_LOGIN("Notice client login"),
    ADMIN_NOTICE_LOGIN("Admin Notice login");

    String value;

    public String getValue() {
        return value;
    }

    Trigger(String value) {
        this.value = value;
    }

}
