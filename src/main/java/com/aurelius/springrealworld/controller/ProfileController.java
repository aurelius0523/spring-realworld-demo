package com.aurelius.springrealworld.controller;

import com.aurelius.springrealworld.facade.ProfileFacade;
import com.aurelius.springrealworld.facade.UserFacade;
import com.aurelius.springrealworld.facade.model.ProfileModel;
import com.aurelius.springrealworld.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileFacade profileFacade;

    public ProfileController(ProfileFacade profileFacade) {this.profileFacade = profileFacade;}

    @GetMapping("/{username}")
    public ProfileModel getProfile(@PathVariable("username") String username) {
        return ProfileModel.builder().username("lol").build();
    }

    @PostMapping("/{username}/follow")
    public ProfileModel followProfileByUsername(@PathVariable("username") String username, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return profileFacade.followProfileByUsername(username, customUserDetails.getUsername());
    }
}
