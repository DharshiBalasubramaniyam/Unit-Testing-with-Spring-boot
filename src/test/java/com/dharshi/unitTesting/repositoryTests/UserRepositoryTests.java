package com.dharshi.unitTesting.repositoryTests;

import com.dharshi.unitTesting.models.User;
import com.dharshi.unitTesting.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        // Define test data
        String name = "test";
        String email = "test@gmail.com";

        // Create a User object with the test data
        User user = User.builder()
                .name(name)
                .email(email)
                .build();

        // Save the user to the database
        userRepository.save(user);

        // Retrieve the user from the database using findByEmail
        User foundUser = userRepository.findByEmail(email);

        // Assert that the retrieved user is not null
        assertThat(foundUser).isNotNull();

        // Assert that the retrieved user's email matches the expected email
        assertThat(foundUser.getEmail()).isEqualTo(email);

        // Assert that the retrieved user's name matches the expected name
        assertThat(foundUser.getName()).isEqualTo(name);
    }

}
