package com.aurelius.springrealworld.controller;

import com.aurelius.springrealworld.facade.ProfileFacade;
import com.aurelius.springrealworld.facade.model.ProfileModel;
import com.aurelius.springrealworld.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileFacade profileFacade;

    public ProfileController(ProfileFacade profileFacade) {this.profileFacade = profileFacade;}

    @GetMapping("/{username}")
    public ProfileModel getProfile(@PathVariable("username") String username,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return profileFacade.getByUsername(username, customUserDetails.getUsername());
    }

    @PostMapping("/{username}/follow")
    public ProfileModel followProfileByUsername(@PathVariable("username") String username,
                                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return profileFacade.followProfileByUsername(username, customUserDetails.getUsername());
    }

    @DeleteMapping("/{username}/follow")
    public ProfileModel unfollowProfileByUsername(@PathVariable("username") String username,
                                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return profileFacade.unfollowProfileByUsername(username, customUserDetails.getUsername());
    }

}
