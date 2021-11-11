package com.aurelius.springrealworld.facade;

import com.aurelius.springrealworld.facade.mapper.ProfileMapper;
import com.aurelius.springrealworld.facade.model.ProfileModel;
import com.aurelius.springrealworld.facade.model.UserModel;
import com.aurelius.springrealworld.repository.UserRepository;
import com.aurelius.springrealworld.repository.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ProfileFacade {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileMapper profileMapper;

    public ProfileModel getByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsernameEqualsIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return profileMapper.toModel(userEntity);
    }

}
