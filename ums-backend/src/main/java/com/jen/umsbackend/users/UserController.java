package com.jen.umsbackend.users;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="/profile")
	public String getUserDetails() {
        return "User profile";
    }
    
    @GetMapping(value="/admin/getUsers")
	public List<UserDTO> listUsers() {
        return userService.getUsers();
    }
    
    @PostMapping(value="/admin/addUser")
    public User addUser(@RequestBody UserDTO userDTO) {
        return userService.editOrAddUser(userDTO);
    }

    @PutMapping(value="/admin/editUser")
    public User editUser(@RequestBody UserDTO userDTO) {
        return userService.editOrAddUser(userDTO);
    }

    @DeleteMapping(value="/admin/deleteUser/{userId}")
    public User deleteUser(@PathVariable("userId") long userId) {
        return userService.deleteUser(userId);
    }
    
}
