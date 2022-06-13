package com.mahkib.basicwebapp.services;

import com.mahkib.basicwebapp.models.User;
import com.mahkib.basicwebapp.repo.UserRepo;
import com.mahkib.basicwebapp.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean
    private UserRepo userRepo;

    private UserService userService;

    @Before
    public void init() {
        userService = new UserService(userRepo);
    }

    @Test
    public void getAllUsers_HappyPath_ShouldReturn1User() {
        // Given
        User user = new User();
        user.setUsername("mahkib");
        user.setPassword("password123");
        user.setRole("USER");
        when(userRepo.findByUsername("mahkib")).thenReturn(user);
        // When
        UserDetails actual = userService.loadUserByUsername("mahkib");

        // Then
        verify(userRepo,times(1)).findByUsername("mahkib");
    }
}
