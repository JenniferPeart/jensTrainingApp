package com.jen.umsbackend.users;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserService implements UserRepository {

    private List<UserDTO> users = new ArrayList<>();

    public UserService() {
        users.add(new UserDTO(1, "jen", "email1"));
        users.add(new UserDTO(2, "jen2", "email2"));
        users.add(new UserDTO(3, "jen3", "email3"));
        users.add(new UserDTO(4, "jen4", "email4"));
        users.add(new UserDTO(5, "jen5", "email5"));
    }

    @Override
    public List<UserDTO> getUsers() {
        return users;
	}
    
}
