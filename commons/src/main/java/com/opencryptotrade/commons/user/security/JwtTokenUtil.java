package com.opencryptotrade.commons.user.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.opencryptotrade.commons.util.PemUtils;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtil {

    @Value("${keycloak.public}")
    private String rsaPublicKey;

    @Value("${keycloak.private}")
    private String rsaPrivateKey;


    public String getUserId(String token) {
        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public DecodedJWT decodeToken(String token) throws Exception {
        try {
            return JWT.decode(token);
        } catch (JWTDecodeException exception){
            throw new Exception("Could not verify JWT token integrity!");
        }
    }

    public boolean validate(String token) throws Exception {
        try {
            RSAPublicKey publicKey = (RSAPublicKey) PemUtils.getPublicKey(PemUtils.loadPEM(rsaPublicKey), "RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey) PemUtils.getPrivateKey(PemUtils.loadPEM(rsaPrivateKey), "RSA");

            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            return true;
        }  catch (JWTDecodeException exception){
            throw new Exception("Could not verify JWT token integrity!");
        } catch (JWTVerificationException exception) {
            throw new Exception("Could not verify JWT token integrity!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
