package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.exception.GenerateRandomKeyException;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.controller.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    public User getUserById(final Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User generateRandomKey(
            final Long userId, final String username, final String email, final String password
    ) throws UserNotFoundException, GenerateRandomKeyException {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        boolean isUsernameValid = user.getUsername().equals(username);
        boolean isEmailValid = user.getEmail().equals(email);
        boolean isPasswordValid = user.getPassword().equals(password);

        if (isUsernameValid && isEmailValid && isPasswordValid && !user.isBlocked()) {
            String key = generateRandomString();
            user.setUserKey(key);
            user.setKeyExpiration(LocalDateTime.now().plusHours(1));
            return userRepository.save(user);
        } else
            throw new GenerateRandomKeyException();
    }

    public boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
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
