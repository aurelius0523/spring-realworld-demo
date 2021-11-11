package com.aurelius.springrealworld.facade.mapper;

import com.aurelius.springrealworld.facade.model.ProfileModel;
import com.aurelius.springrealworld.facade.model.UserModel;
import com.aurelius.springrealworld.repository.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileModel toModel(UserEntity userEntity) {
        return ProfileModel.builder()
//                .isFollowing()
                .bio(userEntity.getBio())
                .username(userEntity.getUsername())
                .image(userEntity.getImage())
                .build();
    }
}
