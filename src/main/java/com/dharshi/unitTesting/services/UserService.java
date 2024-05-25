package com.dharshi.unitTesting.services;

import com.dharshi.unitTesting.dtos.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Object> registerUser(UserDto userDto);

}
