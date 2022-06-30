package com.opencryptotrade.common.config.keycloak;

import java.util.*;
import java.util.stream.Collectors;
import com.opencryptotrade.common.user.dto.UserDto;
import com.opencryptotrade.common.user.model.RoleName;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.util.Collections.emptySet;

/**
 * @see org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter
 */
public final class ReactiveKeycloakJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

	private static final String USERNAME_CLAIM = "preferred_username";
    private static final String ROLES = "roles";
    private static final String CLAIM_REALM_ACCESS = "realm_access";
    private static final String JWT_FIRST_NAME = "given_name";
    private static final String JWT_LAST_NAME = "family_name";
    private static final String JWT_EMAIL = "email";
    private static final String JWT_ID = "id";
	private final Converter<Jwt, Flux<GrantedAuthority>> jwtGrantedAuthoritiesConverter;

	public ReactiveKeycloakJwtAuthenticationConverter(Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter) {
		Assert.notNull(jwtGrantedAuthoritiesConverter, "jwtGrantedAuthoritiesConverter cannot be null");
		this.jwtGrantedAuthoritiesConverter = new ReactiveJwtGrantedAuthoritiesConverterAdapter(jwtGrantedAuthoritiesConverter);
	}

	@Override
	public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
		return this.jwtGrantedAuthoritiesConverter.convert(jwt)
				.collectList()
				.map(authorities -> {
                    UserDto user = getUserFromJwt(jwt);
                    OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, jwt.getTokenValue(),
                            jwt.getIssuedAt(), jwt.getExpiresAt());
                    return new BearerTokenAuthentication(user, accessToken, authorities);
                });
	}


    private static UserDto getUserFromJwt(Jwt jwt) {
        UserDto user = new UserDto();
        user.setId((String) jwt.getClaims().get(JWT_ID));
        user.setUsername((String) jwt.getClaims().get(USERNAME_CLAIM));
        user.setFirstName((String) jwt.getClaims().get(JWT_FIRST_NAME));
        user.setLastName((String) jwt.getClaims().get(JWT_LAST_NAME));
        user.setEmail((String) jwt.getClaims().get(JWT_EMAIL));
        user.setRoles(realmRoles(jwt));
        return user;
    }

    private static Set<RoleName> realmRoles(Jwt jwt) {
        return Optional.ofNullable(jwt.getClaimAsMap(CLAIM_REALM_ACCESS))
                .map(realmAccess -> ((List<String>) realmAccess.get(ROLES)).stream().map(RoleName::valueOf).collect(Collectors.toSet()))
                .orElse(emptySet());
    }
}