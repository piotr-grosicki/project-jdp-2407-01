package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> blockUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User u = user.get();
            u.setBlocked(true);
            userRepository.save(u);
        }
        return user;
    }

    public Optional<User> generateRandomKey(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User u = user.get();
            String key = generateRandomString();
            u.setUserKey(key);
            userRepository.save(u);
            return Optional.of(u);
        }
        return Optional.empty();
    }

    private String generateRandomString() {
        String characters = "ABCD0123456789";
        StringBuilder sb = new StringBuilder(24);
        for (int i = 0; i < 24; i++) {
            sb.append(characters.charAt(new Random().nextInt(characters.length())));
        }
        return sb.toString();
    }
}
