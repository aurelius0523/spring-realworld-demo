package com.aurelius.springrealworld.facade;

import com.aurelius.springrealworld.facade.mapper.ProfileMapper;
import com.aurelius.springrealworld.facade.model.ProfileModel;
import com.aurelius.springrealworld.facade.model.UserModel;
import com.aurelius.springrealworld.repository.UserRepository;
import com.aurelius.springrealworld.repository.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProfileFacade {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileMapper profileMapper;

    public ProfileModel getByUsername(String username, String followerUsername) {
        UserEntity userEntity = userRepository.findByUsernameEqualsIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return profileMapper.toModel(userEntity, followerUsername);
    }

    public ProfileModel followProfileByUsername(String followeeUsername, String followerUsername) {
        UserEntity followeeUserEntity = userRepository.findByUsernameEqualsIgnoreCase(followeeUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        UserEntity followerUserEntity = userRepository.findByUsernameEqualsIgnoreCase(followerUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        followeeUserEntity.getFollowers().add(followerUserEntity);
        userRepository.save(followeeUserEntity);

        return profileMapper.toModel(followeeUserEntity, followerUsername);
    }

    public ProfileModel unfollowProfileByUsername(String followeeUsername, String followerUsername) {
        UserEntity followeeUserEntity = userRepository.findByUsernameEqualsIgnoreCase(followeeUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        UserEntity followerUserEntity = userRepository.findByUsernameEqualsIgnoreCase(followerUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        followeeUserEntity.getFollowers().remove(followerUserEntity);
        userRepository.save(followeeUserEntity);

        return profileMapper.toModel(followeeUserEntity, followerUsername);
    }
}
