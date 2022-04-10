package com.opencryptotrade.common.validator;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The list of error description records.
     */
    private final List<ErrorInfo> errors = new ArrayList<>();

    /**
     * The optional error code that might be relevant in some business contexts.
     */
    private String code;

    public ErrorResponse(@NonNull List<ErrorInfo> errors) {
        this.errors.addAll(errors);
    }

    public ErrorResponse(@NonNull String errorMessage) {
        this.errors.add(new ErrorInfo(errorMessage));
    }

    public ErrorResponse(@NonNull String errorMessage, @NonNull String code) {
        this.errors.add(new ErrorInfo(errorMessage));
        this.code = code;
    }

}
