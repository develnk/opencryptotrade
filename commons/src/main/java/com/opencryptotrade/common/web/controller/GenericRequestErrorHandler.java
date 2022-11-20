package com.opencryptotrade.common.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencryptotrade.common.validator.ErrorInfo;
import com.opencryptotrade.common.validator.ErrorResponse;
import com.opencryptotrade.common.web.MessageResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GenericRequestErrorHandler {

    private final MessageResolver messageResolver;

    @Autowired
    public GenericRequestErrorHandler(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInvalidRequestData(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        LOGGER.warn("{} data validator error{} found in request payload. Returning response with error messages...",
                bindingResult.getErrorCount(), bindingResult.getErrorCount() > 1 ? "s" : "");
        List<ErrorInfo> errors = messageResolver.getValidationErrorsFromBindingResult(e.getBindingResult());
        LOGGER.debug("Request Payload Validation Errors: {}", errors);
        return new ErrorResponse(errors);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalidJwt(AuthenticationException e) {
        LOGGER.warn("User not found or invalid and cannot be authenticated. Request not authorized.", e);
        return new ErrorResponse(e.getMessage());
    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public void handleAccessDeniedException(AccessDeniedException e, HttpServletResponse response) throws IOException {
//        LOGGER.warn("Request not authorized.", e);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpStatus.FORBIDDEN.value());
//        response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorResponse(e.getMessage())));
//        response.getWriter().flush();
//        response.getWriter().close();
//    }

}
