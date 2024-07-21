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
    private final Random random = new Random();

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void blockUser(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User userToBlock = user.get();
            userToBlock.setBlocked(true);
            userRepository.save(userToBlock);
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    public String generateRandomKey(Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return generateRandomString();
        } else {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
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
