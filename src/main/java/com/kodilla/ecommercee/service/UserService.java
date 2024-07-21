package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Optional<User> generateRandomKey(Long userId, String username, String email, String password) {

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();
        boolean isUsernameValid = user.getUsername().equals(username);
        boolean isEmailValid = user.getEmail().equals(email);
        boolean isPasswordValid = user.getPassword().equals(password);

        if (isUsernameValid && isEmailValid && isPasswordValid && !user.isBlocked()) {
            String key = generateRandomString();
            user.setUserKey(key);
            user.setKeyExpiration(LocalDateTime.now().plusHours(1));
            userRepository.save(user);
            return Optional.of(user);
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
