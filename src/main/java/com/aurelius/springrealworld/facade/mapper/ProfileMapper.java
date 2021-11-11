package com.aurelius.springrealworld.facade.mapper;

import com.aurelius.springrealworld.facade.model.ProfileModel;
import com.aurelius.springrealworld.repository.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileModel toModel(UserEntity userEntity) {
        return ProfileModel.builder()
                .bio(userEntity.getBio())
                .username(userEntity.getUsername())
                .image(userEntity.getImage())
                .build();
    }

    public ProfileModel toModel(UserEntity userEntity, String followerUsername) {
        ProfileModel profileModel = toModel(userEntity);
        profileModel.setFollowing(userEntity.isFollower(followerUsername));

        return profileModel;
    }
}
