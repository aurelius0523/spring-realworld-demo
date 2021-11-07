package com.aurelius.springrealworld.security;

import com.aurelius.springrealworld.repository.entities.UserEntity;
import liquibase.pro.packaged.U;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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

        System.out.println(token);

        System.out.println(jwtTokenUtil.getUsername(token));
        System.out.println(jwtTokenUtil.getExpirationDate(token));
        Assertions.assertTrue(jwtTokenUtil.validate(token));
    }
}