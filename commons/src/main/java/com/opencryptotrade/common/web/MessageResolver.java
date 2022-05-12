package com.opencryptotrade.common.web;

import com.opencryptotrade.common.validator.ErrorInfo;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class MessageResolver {

    private final MessageSource messageSource;

    public MessageResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public List<ErrorInfo> getValidationErrorsFromBindingResult(Errors bindingResult) {
        List<ErrorInfo> validationErrors = new ArrayList<>(bindingResult.getErrorCount());
        if (bindingResult.hasErrors()) {
            // first, collect all object-global errors (not specific to particular field) - if any found - and add to validationErrors list
            validationErrors = bindingResult.getGlobalErrors().stream()
                    .map(globalError -> new ErrorInfo(messageSource.getMessage(globalError, Locale.getDefault())))
                    .collect(Collectors.toCollection(() -> new ArrayList<>(bindingResult.getErrorCount())));

            // now, add any field-specific errors to validationErrors
            bindingResult.getFieldErrors().stream()
                    .map(fieldError -> new ErrorInfo(fieldError.getField(), fieldError.getRejectedValue(), null, messageSource.getMessage(fieldError, Locale.getDefault())))
                    .forEach(validationErrors::add);
        }
        return validationErrors;
    }

    public String getMessage(String msgCode, String defaultMessage, Locale locale) {
        return messageSource.getMessage(msgCode, null, defaultMessage, locale);
    }
}
