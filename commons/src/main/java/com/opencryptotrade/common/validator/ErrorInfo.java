package com.opencryptotrade.common.validator;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.io.Serializable;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ErrorInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The property path to the field value from the root of the data object; may be null if the error is not property-specific and relates to the
     * data object in general.
     */
    private String fieldPath;

    /**
     * The property value that caused the error; may be null if the error is not property-specific and relates to the data object in general.
     */
    private Object offendingValue;

    /**
     * The expected correct value, type description, or range of values for the property, if relevant; may be {@code null}.
     */
    private String expected;

    /**
     * The error detail message.
     */
    @NonNull
    private String message;

    /**
     * Constructs an instance with only the given error message.
     *
     * @param errorMessage message to set for this error
     */
    public ErrorInfo(@NonNull String errorMessage) {
        message = errorMessage;
    }

    public ErrorInfo(String fieldPath, Object offendingValue, String expected, @NonNull String message) {
        this.fieldPath = fieldPath;
        this.offendingValue = offendingValue;
        this.expected = expected;
        this.message = message;
    }

}
