package com.jen.umsbackend.users;

// import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// interacts with the database
public interface UserRepository {
    List<UserDTO> getUsers();
    
}
