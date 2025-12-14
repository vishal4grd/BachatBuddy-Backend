package com.Mystartup.BacahatBuddy.Repository;

import com.Mystartup.BacahatBuddy.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
     User findByUsernameAndPassword(String username, String password);

     @Query("SELECT u FROM User u WHERE TRIM(LOWER(u.username)) = LOWER(:username) AND TRIM(u.password) = :password")
     User login(@Param("username") String username, @Param("password") String password);

     // Find user by username for JWT authentication
     User findByUsername(String username);

}
