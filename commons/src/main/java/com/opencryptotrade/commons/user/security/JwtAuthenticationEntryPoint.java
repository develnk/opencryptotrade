package com.opencryptotrade.commons.user.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
            throws IOException {
        String message = String.format("%s (%s : Unauthorized.)  Valid JWT expected in request. Access denied to URL: %s",
                authenticationException.getMessage(), HttpServletResponse.SC_UNAUTHORIZED, request.getRequestURL());
        LOGGER.warn(message);
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        String responseJson = String.format("{\"message\": \"%s\"}", message);
        response.getWriter().write(responseJson);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
