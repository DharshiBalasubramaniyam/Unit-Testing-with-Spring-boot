package com.dharshi.unitTesting.controllers;

import com.dharshi.unitTesting.dtos.UserDto;
import com.dharshi.unitTesting.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping("/new")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }

}
