package com.jen.umsbackend;

import com.jen.umsbackend.users.User;
import com.jen.umsbackend.users.UserDTO;
import com.jen.umsbackend.users.UserService;
import com.jen.umsbackend.users.UserRepository;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldGetUsers() {

        // Mock what the repository returns
        User testUser = new User("TestName", "Test@hotmail.com");
        List<User> usersList = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(usersList);

        // want userService's getUsers method to return a List of UserDTOs
        List<UserDTO> usersDTOList = userService.getUsers();

        assertEquals(usersList.get(0).getFullName(), usersDTOList.get(0).getFullName());
    }

    @Test
    public void shouldEditOrAddUser() {
        // should take in a userDTO (from the client), convert it to a user
        // then save that user to db using userRepo, returning the user with it's id

        // Arrange
        UserDTO testUserDTO = new UserDTO("TestName", "Test@hotmail.com");
        User testUser = new User(5, "TestName", "Test@hotmail.com");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        User savedUser = userService.editOrAddUser(testUserDTO);
        
        // Assert
        // that the expected returned user (test user) matches the actual returned (saved) user
        assertEquals(testUser.getFullName(), savedUser.getFullName());
        
        // that userRepository.save() was called once
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void shouldDeleteUser() {
        // should take in a userDTO (from the client), convert it to a user
        // then delete that user from the db using userRepo, returning void

        // Arrange
        UserDTO testUserDTO = new UserDTO(5, "TestName", "Test@hotmail.com");

        // Act
        userService.deleteUser(testUserDTO);
        
        // that userRepository.save() was called once
        verify(userRepository, times(1)).delete(any(User.class));
    }

}
