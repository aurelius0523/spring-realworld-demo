package com.aurelius.springrealworld.facade;

import com.aurelius.springrealworld.controller.request.CreateUserRequest;
import com.aurelius.springrealworld.exception.BusinessValidationException;
import com.aurelius.springrealworld.facade.mapper.UserMapper;
import com.aurelius.springrealworld.facade.model.UserModel;
import com.aurelius.springrealworld.repository.UserRepository;
import com.aurelius.springrealworld.repository.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserFacade {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserModel createUser(CreateUserRequest request) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(request.getUsername());

        if (optionalUserEntity.isPresent()) {
            throw new BusinessValidationException("User already exists");
        }

        UserEntity userEntity = UserEntity.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .build();

        return userMapper.toModel(userRepository.save(userEntity));
    }
}
