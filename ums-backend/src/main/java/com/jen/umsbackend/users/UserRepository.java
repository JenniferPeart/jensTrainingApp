package com.jen.umsbackend.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// interacts with the database
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
