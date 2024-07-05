package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> blockUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        userOpt.ifPresent(user -> {
            user.setBlocked(true);
            userRepository.save(user);
        });
        return userOpt;
    }

    public String generateRandomKey(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            SecureRandom secureRandom = new SecureRandom();
            byte[] key = new byte[24];
            secureRandom.nextBytes(key);
            return Base64.getEncoder().encodeToString(key);
        }
        return null;
    }
}
