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
    public void shouldAddUser() {
        // want a user to be added to the db and return the status

        //arrange
        UserDTO testUser = new UserDTO("TestName", "Test@hotmail.com");
        boolean expectedStatus = true;
        when(userRepository.save(any(User.class))).thenReturn(any(User.class));

        //act
        userService.addUser(testUser);
        
        //assert
        assertEquals(expectedStatus, userService.addUser(testUser));
        verify(userRepository, times(1)).save(any(User.class));

    }

}
