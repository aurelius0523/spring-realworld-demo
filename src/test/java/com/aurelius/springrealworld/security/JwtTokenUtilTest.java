package com.aurelius.springrealworld.security;

import com.aurelius.springrealworld.repository.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtTokenUtilTest {

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void generateAccessToken() {
        UserEntity userEntity = UserEntity.builder()
                .username("kimloong")
                .build();
        String token = jwtTokenUtil.generateAccessToken(userEntity);
        Assertions.assertTrue(jwtTokenUtil.isJwtTokenValid(token));
    }
}