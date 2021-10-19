package com.aurelius.springrealworld.controller;

import com.aurelius.springrealworld.controller.request.CreateUserRequest;
import com.aurelius.springrealworld.facade.UserFacade;
import com.aurelius.springrealworld.facade.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserFacade userFacade;

    @PostMapping("/users")
    public UserModel createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
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
//    @GetMapping("/users")
//    public List<UserModel> getUserList() {
//        return new ArrayList<>();
//    }
//
//    @PostMapping("/users/login")
//    public UserModel login() {
//        return null;
//    }
}
