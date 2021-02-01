package com.jen.umsbackend;

import com.jen.umsbackend.users.User;
import com.jen.umsbackend.users.UserDTO;
import com.jen.umsbackend.users.UserService;
import com.jen.umsbackend.users.UserRepository;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
        User testUser = new User();
        testUser.setFullName("TestName");
        testUser.setEmail("Test@hotmail.com");
        List<User> usersList = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(usersList);

        // want userService's getUsers method to return a List of UserDTOs
        List<UserDTO> usersDTOList = userService.getUsers();

        assertEquals(usersList.get(0).getFullName(), usersDTOList.get(0).getFullName());
    }


}
