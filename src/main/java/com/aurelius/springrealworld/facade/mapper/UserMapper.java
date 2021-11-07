package com.aurelius.springrealworld.facade.mapper;

import com.aurelius.springrealworld.facade.model.UserModel;
import com.aurelius.springrealworld.facade.model.PageModel;
import com.aurelius.springrealworld.facade.model.UserModel;
import com.aurelius.springrealworld.repository.entities.UserEntity;
import com.aurelius.springrealworld.repository.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper extends PageMapper<UserEntity, UserModel>{
    public UserModel toModel(UserEntity userEntity) {
        return UserModel.builder()
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
