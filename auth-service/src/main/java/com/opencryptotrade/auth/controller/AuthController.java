package com.opencryptotrade.auth.controller;

import com.opencryptotrade.auth.commons.user.model.SystemUserDetails;
import com.opencryptotrade.auth.commons.web.model.AuthRequest;
import com.opencryptotrade.auth.commons.web.model.AuthResponse;
import com.opencryptotrade.auth.service.SystemUserDetailsService;
import com.opencryptotrade.auth.commons.config.JWTUtil;
import com.opencryptotrade.auth.commons.config.PBKDF2Encoder;
import com.opencryptotrade.common.validator.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final JWTUtil jwtUtil;
    private final PBKDF2Encoder passwordEncoder;
    private final SystemUserDetailsService userService;


    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar) {
        return userService.findByUsername(ar.getUsername())
                .cast(SystemUserDetails.class)
                .filter(userDetails -> passwordEncoder.matches(ar.getPassword().concat(userDetails.getSalt()), userDetails.getPassword()))
                .map(userDetails -> ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    // Reset password

    // me

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCommonNotFound(RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }
}
