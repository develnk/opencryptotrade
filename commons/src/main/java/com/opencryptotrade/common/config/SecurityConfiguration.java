package com.opencryptotrade.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter) {
		http.requestCache().disable()
                .csrf().disable()
                .httpBasic().disable()
                .logout().disable()
                .formLogin().disable()
                .authorizeExchange()
                .pathMatchers("/api/**").authenticated()
				.anyExchange().denyAll()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint((exchange, exception) -> Mono.error(exception))
                    .accessDeniedHandler((exchange, exception) -> Mono.error(exception))
                .and()
                    .oauth2ResourceServer()
                        .jwt()
                        .jwtAuthenticationConverter(jwtAuthenticationConverter);

		return http.build();
	}
}
