package com.aurelius.springrealworld.controller;

import com.aurelius.springrealworld.facade.UserFacade;
import com.aurelius.springrealworld.facade.model.ProfileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    @Autowired
    private UserFacade userFacade;
    @GetMapping("/{username}")
    public ProfileModel getProfile(@PathVariable("username") String username) {

       return ProfileModel.builder().username("lol").build();
    }
}
