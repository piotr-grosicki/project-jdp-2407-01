package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Random random = new Random();

    public void createUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> blockUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        userOptional.ifPresent(user -> {
            user.setBlocked(true);
            userRepository.save(user);
        });
        return userOptional;
    }

    public String generateRandomKey(Long userId) {
        if (userRepository.existsById(userId)) {
            return generateRandomString();
        }
        return null;
    }

    private String generateRandomString() {
        String characters = "ABCD0123456789";
        StringBuilder sb = new StringBuilder(24);
        for (int i = 0; i < 24; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
