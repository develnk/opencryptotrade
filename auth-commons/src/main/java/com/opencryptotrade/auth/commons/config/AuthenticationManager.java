package com.opencryptotrade.auth.commons.config;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTUtil jwtUtil;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        return Mono.just(jwtUtil.validateToken(authToken))
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
                    List<String> rolesMap = claims.get("role", List.class);
                    return new UsernamePasswordAuthenticationToken(
                            jwtUtil.getUsernameFromToken(authToken),
                            null,
                            rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                    );
                });
    }
}