package com.aurelius.springrealworld.controller;

import com.aurelius.springrealworld.controller.request.CreateUserRequest;
import com.aurelius.springrealworld.controller.request.LoginRequest;
import com.aurelius.springrealworld.facade.UserFacade;
import com.aurelius.springrealworld.facade.model.PageModel;
import com.aurelius.springrealworld.facade.model.UserModel;
import com.aurelius.springrealworld.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserFacade userFacade;

    @PostMapping("/register")
    public UserModel registerUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userFacade.createUser(createUserRequest);
    }

    //    @GetMapping("/user")
//    public UserModel getCurrentUser() {
//        return null;
//    }
//
//    @PutMapping("/user")
//    public UserModel updateCurrentUser() {
//        return null;
//    }
//
    @GetMapping("/users")
    public PageModel<UserModel> getUserList(
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset) {
        return userFacade.getUserList(limit, offset);
    }

    @PostMapping("/users/login")
    public UserModel login(@Valid @RequestBody LoginRequest loginRequest) {
        return userFacade.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
}
