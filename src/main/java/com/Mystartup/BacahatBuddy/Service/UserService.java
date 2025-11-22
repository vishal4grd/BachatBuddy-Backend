package com.Mystartup.BacahatBuddy.Service;

import com.Mystartup.BacahatBuddy.Model.User;
import com.Mystartup.BacahatBuddy.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getUserById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return repo.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = repo.findById(id).orElse(null);
        if (user != null) {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return repo.save(user);
        }
        return null;
    }

    public User authenticateUser(String username, String password) {
        return repo.login(username.trim(), password.trim());
    }


    public User deleteUser(Long id) {
        User user = repo.findById(id).orElse(null);
        if (user != null) {
            repo.deleteById(id);
        }
        return user;
    }
}

