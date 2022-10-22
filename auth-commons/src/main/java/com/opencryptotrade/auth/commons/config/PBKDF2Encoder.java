package com.opencryptotrade.auth.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
public class PBKDF2Encoder implements PasswordEncoder {

    @Value("${opencryptotrade.jjwt.password.encoder.secret}")
    private String secret;

    @Value("${opencryptotrade.jjwt.password.encoder.iteration}")
    private Integer iteration;

    @Value("${opencryptotrade.jjwt.password.encoder.keylength}")
    private Integer keylength;

    /**
     * @param rawPassword password
     * @return encoded password
     */
    @Override
    public String encode(CharSequence rawPassword) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(rawPassword.toString().toCharArray(), secret.getBytes(), iteration, keylength))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }

}
