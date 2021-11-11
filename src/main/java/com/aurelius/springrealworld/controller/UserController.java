package com.aurelius.springrealworld.controller;

import com.aurelius.springrealworld.controller.request.CreateUserRequest;
import com.aurelius.springrealworld.controller.request.LoginRequest;
import com.aurelius.springrealworld.controller.request.UpdateUserRequest;
import com.aurelius.springrealworld.facade.UserFacade;
import com.aurelius.springrealworld.facade.model.PageModel;
import com.aurelius.springrealworld.facade.model.UserModel;
import com.aurelius.springrealworld.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {this.userFacade = userFacade;}

    @PostMapping("/register")
    public UserModel registerUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userFacade.createUser(createUserRequest);
    }

    @GetMapping("/user")
    public UserModel getCurrentUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return userFacade.getByUsername(customUserDetails.getUsername());
    }

    @PutMapping("/user")
    public UserModel updateCurrentUser(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UpdateUserRequest updateUserRequest) {
        return userFacade.updateUser(customUserDetails.getUsername(), updateUserRequest);
    }

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
