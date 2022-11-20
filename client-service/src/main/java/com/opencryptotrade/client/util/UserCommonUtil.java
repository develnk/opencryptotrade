package com.opencryptotrade.client.util;

import com.opencryptotrade.auth.commons.config.PBKDF2Encoder;
import com.opencryptotrade.common.reactive.model.entity.mongo.ProtectedUser;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserCommonUtil {

    private final PBKDF2Encoder passwordEncoder;

    public ProtectedUser generateProtectedUser(final String userName, final String password) {
        String salt = RandomStringUtils.random(15, true, true);
        ProtectedUser protectedUser = new ProtectedUser();
        protectedUser.setUsername(userName);
        protectedUser.setSalt(salt);
        protectedUser.setPassword(passwordEncoder.encode(password.concat(salt)));
        return protectedUser;
    }

}
