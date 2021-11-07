package com.aurelius.springrealworld.facade;

import com.aurelius.springrealworld.controller.request.CreateUserRequest;
import com.aurelius.springrealworld.exception.BusinessValidationException;
import com.aurelius.springrealworld.facade.mapper.UserMapper;
import com.aurelius.springrealworld.facade.model.PageModel;
import com.aurelius.springrealworld.facade.model.UserModel;
import com.aurelius.springrealworld.repository.UserRepository;
import com.aurelius.springrealworld.repository.entities.UserEntity;
import com.aurelius.springrealworld.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserFacade {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    public UserFacade(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public UserModel createUser(CreateUserRequest request) {
        checkIfUsernameExists(request.getUsername());

        UserEntity savedUserEntity = userRepository.save(UserEntity.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .build());

        return userMapper.toModel(savedUserEntity, jwtTokenUtil.generateAccessToken(savedUserEntity));
    }

    public PageModel<UserModel> getUserList(int limit, int offset) {
        return userMapper.fromPageModel(userRepository.findAll(PageRequest.of(offset, limit)));
    }

    public UserModel login(String username, String password) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailEqualsIgnoreCaseAndPasswordEquals(username, password);

        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> {throw new BusinessValidationException("Invalid username or password");});

        return userMapper.toModel(userEntity, jwtTokenUtil.generateAccessToken(userEntity));
    }

    protected void checkIfUsernameExists(String username) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);

        if (optionalUserEntity.isPresent()) {
            throw new BusinessValidationException("User already exists");
        }
    }
}

