package com.jen.umsbackend.users;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping(value="/getUsers")
	public List<UserDTO> listUsers() {
        return userService.getUsers();
    }
    
    @PostMapping(value="/addUser")
    public User addUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userDTO);
    }
    
}
