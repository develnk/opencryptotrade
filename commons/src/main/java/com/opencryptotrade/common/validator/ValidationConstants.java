package com.opencryptotrade.common.validator;

public final class ValidationConstants {

    public static final String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})";
    public static final String URL_REGEX = "^(https?:\\/\\/)?(www\\.)?([\\w\\Q$-_+!*'(),%\\E]+\\.)+[\u200C\u200B\\w]{2,63}\\/?$";
    public static final String PHONE_REGEX = "^([/+]?([1-9]{0,1})?)((([0-9]{1}[- .]?)?[\\(]?([0-9]{3})[-.\\)]?[ ]?[0-9]{3}[- .]?[0-9]{4})+)*$";

    /**
     * The regular expression that enforces the following password requirements:
     * <ul>
     *     <li>At least one digit [0-9]</li>
     *     <li>At least one uppercase character [A-Z]</li>
     *     <li>At least one lowercase character [a-z]</li>
     *     <li>At least one special character [*.!@#$%^&(){}[]:;<>,.?/~_+-=|\]</li>
     *     <li>At least 8 characters in length, but no more than 32.</li>
     * </ul>
     */
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,32}$";

    private ValidationConstants() {
    }

}
