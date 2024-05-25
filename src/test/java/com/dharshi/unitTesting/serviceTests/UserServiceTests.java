package com.dharshi.unitTesting.serviceTests;

import com.dharshi.unitTesting.dtos.UserDto;
import com.dharshi.unitTesting.models.User;
import com.dharshi.unitTesting.repositories.UserRepository;
import com.dharshi.unitTesting.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void registerUserSuccess() {
        // Create a UserDto object with test data
        UserDto userDto = UserDto.builder()
                .name("test")
                .email("test@gmail.com")
                .build();

        // Mock the userRepository.findByEmail method to return null,
        // simulating that no user exists with the provided email
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(null);

        // Call the registerUser method on the userService with the userDto
        ResponseEntity<?> response = userService.registerUser(userDto);

        // Verify that the findByEmail method was called exactly once with the given email
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());

        // Verify that the save method was called exactly once with any User object
        verify(userRepository, times(1)).save(any(User.class));

        // Assert that the response status code is HttpStatus.CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Success: User details successfully saved!", response.getBody());
    }

    @Test
    public void registerUserWithAlreadyExistsEmailFail() {
        // Create a UserDto object with test data
        UserDto userDto = UserDto.builder()
                .name("test")
                .email("test@gmail.com")
                .build();

        // Mock the userRepository.findByEmail method to return a new User object,
        // simulating that a user already exists with the provided email
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(new User());

        // Call the registerUser method on the userService with the userDto
        ResponseEntity<?> response = userService.registerUser(userDto);

        // Verify that the findByEmail method was called exactly once with the given email
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());

        // Verify that the save method was not called, as the email already exists
        verify(userRepository, times(0)).save(any(User.class));

        // Assert that the response status code is HttpStatus.CONFLICT
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Fail: Email already exists!", response.getBody());
    }

    @Test
    public void registerUserWithInternalServerErrorFail() {
        // Create a UserDto object with test data
        UserDto userDto = UserDto.builder()
                .name("test")
                .email("test@gmail.com")
                .build();

        // Mock the userRepository.findByEmail method to throw a RuntimeException,
        // simulating an unexpected error during the database operation
        when(userRepository.findByEmail(userDto.getEmail())).thenThrow(new RuntimeException());

        // Call the registerUser method on the userService with the userDto
        ResponseEntity<?> response = userService.registerUser(userDto);

        // Verify that the findByEmail method was called exactly once with the given email
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());


        // Verify that the save method was not called due to the exception
        verify(userRepository, times(0)).save(any(User.class));

        // Assert that the response status code is HttpStatus.INTERNAL_SERVER_ERROR
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Fail: Failed to process request now. Try again later", response.getBody());
    }

}
