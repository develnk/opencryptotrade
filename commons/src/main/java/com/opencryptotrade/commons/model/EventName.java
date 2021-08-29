package com.opencryptotrade.commons.model;

import lombok.Getter;

@SuppressWarnings("FieldNamingConvention")
public enum EventName {

    NONE,  // only used to init the default value on @Event annotation when event name is not set [CAV]

    // EMPLOYEE ACTIONS
    WHEN_A_EMPLOYEE_LOGS_IN("Employee ${currentUser} logged in."),
    WHEN_A_EMPLOYEE_LOGS_IN_FAILED("Employee ${accountId} logged in fail."),
    WHEN_A_EMPLOYEE_CREATES_AN_EMAIL_TEMPLATE(""),
    WHEN_A_EMPLOYEE_UPDATES_AN_EMAIL_TEMPLATE(""),
    WHEN_A_EMPLOYEE_DELETES_AN_EMAIL_TEMPLATE(""),
    WHEN_A_EMPLOYEE_CREATES_AN_TEMPLATE_FOLDER(""),
    WHEN_A_EMPLOYEE_UPDATES_AN_TEMPLATE_FOLDER(""),
    WHEN_A_EMPLOYEE_DELETES_AN_TEMPLATE_FOLDER(""),


    // CUSTOMER ACTIONS,
    WHEN_A_CUSTOMER_LOGS_IN("Customer ${currentUser} logged in."),
    WHEN_A_CUSTOMER_LOGS_IN_FAILED("Customer ${accountId} logged in fail.");

    // SYSTEM ACTIONS

    /**
     * The parameterized message template for this event name; the template may contain generic {@code %s} placeholders to be replaced with arbitrary
     * string arguments passed to the {@link #getMessage(String...)} method, or they may be one or more of the following:
     * <ul>
     *     <li>${accountId}</li> - will be replaced with the {@code ApplicationEvent.accountID} value if available
     * </ul>
     */
    @Getter
    private String messageTemplate;

    EventName() {
    }

    EventName(String msgTemplate) {
        this.messageTemplate = msgTemplate;
    }

    /**
     * Gets the enum-specific message; if any string arguments are provided, attempts to insert them into the enum-specific message template.
     *
     * @param args 0 or more string arguments to replace the placeholders in the message template, if appropriate; extra args are ignored
     * @return event message
     */
    public String getMessage(String... args) {
        return messageTemplate != null ? String.format(messageTemplate, args) : "";
    }
}
