package com.jen.umsbackend.users;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public List<UserDTO> getUsers() {
        // want get users from db, then change List<User> into List<UserDTO> 
        List<User> users = userRepository.findAll(); 
        ModelMapper mapper = new ModelMapper();
        return users
            .stream()
            .map(user -> mapper.map(user, UserDTO.class))
            .collect(Collectors.toList());
    }
    
    public User editOrAddUser(UserDTO userDTO) {
        // receive a userDTO from the client
        // convert it to a user
        // save that user to db    

        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(userDTO, User.class);
        return userRepository.save(user);

    }
    
}
