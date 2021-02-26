package com.jen.umsbackend.users;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        // INITIALISE DATABSE
        
        // User userA = new User("jen", passwordEncoder.encode("password"), "Jennifer Peart", "jen@email.com", "ADMIN");
        // User userB = new User("willsmith", passwordEncoder.encode("password"), "Will Smith", "willsmith@hotmail.com", "");
        // User userC = new User("emmastone", passwordEncoder.encode("password"), "Emma Stone", "emmastone@gmail.co.uk", "");
        // User userD = new User("johnwilliams", passwordEncoder.encode("password"), "John Williams", "john@gmail.com", "ADMIN");

        // userRepository.deleteAll();

        // userRepository.save(userA);
        // userRepository.save(userB);
        // userRepository.save(userC);
        // userRepository.save(userD);
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

    public User deleteUser(long userId) {
        User user = userRepository.findById(userId).get();
        userRepository.deleteById(userId);
        return user;
    }
    
}
