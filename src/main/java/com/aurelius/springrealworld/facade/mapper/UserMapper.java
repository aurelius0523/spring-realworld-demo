package com.aurelius.springrealworld.facade.mapper;

import com.aurelius.springrealworld.facade.model.PageModel;
import com.aurelius.springrealworld.facade.model.UserModel;
import com.aurelius.springrealworld.repository.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper extends PageMapper<UserEntity, UserModel> {
    /**
     * Enrich {@link UserModel} with access token
     *
     * @param userEntity user entity
     * @param token JWT token
     * @return {@link UserModel} with token field
     */
    public UserModel toModel(UserEntity userEntity, String token) {
        UserModel userModel = toModel(userEntity);
        userModel.setToken(token);

        return userModel;
    }

    public UserModel toModel(UserEntity userEntity) {
        return UserModel.builder()
                .id(String.valueOf(userEntity.getId()))
                .bio(userEntity.getBio())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .image(userEntity.getImage())
                .build();
    }

    public PageModel<UserModel> fromPageModel(Page<UserEntity> userEntityPage) {
        PageModel<UserModel> pagedUserModel = super.fromPage(userEntityPage);
        pagedUserModel.setData(userEntityPage
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList()));

        return pagedUserModel;
    }
}
