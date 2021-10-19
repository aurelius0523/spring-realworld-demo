package com.aurelius.springrealworld.facade.mapper;

import com.aurelius.springrealworld.facade.model.UserModel;
import com.aurelius.springrealworld.repository.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserModel toModel(UserEntity userEntity) {
        return UserModel.builder()
                .bio(userEntity.getBio())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .image(userEntity.getImage())
                .build();
    }
}
