package com.opencryptotrade.commons.user.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.opencryptotrade.commons.user.security.services.UserDetailsImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    static final String AUTH_TOKEN_REQUEST_PARAM_NAME = "auth";

    @Getter
    private final JwtConfigProperties jwtConfigProperties;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
        jwtConfigProperties = new JwtConfigProperties();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtHeader = getJwtHeader(request);

        if (jwtHeader == null) {
            LOGGER.debug("No header with JWT found in request... Proceeding to next filter in chain.");
            filterChain.doFilter(request, response);
            return;
        }

        LOGGER.debug("Found JWT in request header.");
        String token = jwtHeader.replace(jwtConfigProperties.getPrefix(), "").replace(" ", "");
        DecodedJWT decodedToken = null;

        try {
            decodedToken = jwtTokenUtil.decodeToken(token);

            if (!jwtTokenUtil.validate(token)) {
                filterChain.doFilter(request, response);
                return;
            }
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage());
        }


        if (decodedToken != null) {
            String userName = decodedToken.getPayload();
            // Get user identity and set it on the spring security context
        if (userName != null) {
//            UserDetails userDetails = new UserDetailsImpl();
//            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                    new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities());
//            usernamePasswordAuthenticationToken
//                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtHeader(HttpServletRequest request) {
        String jwtHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtHeader == null) {
            LOGGER.debug("No header with JWT found in original request... Checking for presence of the auth request parameter....");
            jwtHeader = getJwtByResolvingRequestParam(request);
        }
        return jwtHeader;
    }

    private String getJwtByResolvingRequestParam(ServletRequest request) {
        String authTokenString = request.getParameter(AUTH_TOKEN_REQUEST_PARAM_NAME);
        if (StringUtils.isNotBlank(authTokenString)) {
            LOGGER.debug("Found '{}' request parameter with encrypted auth token. Processing credentials to generate JWT....");
//            return jwtManager.authenticateAndGetJwtHeader(authTokenString);
        }
        return null;
    }

}
