package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.CreateUserDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("v1/shop/users")
public class UserController {

    private final Random random = new Random();

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserDto createUserDTO) {
        String message = "User " + createUserDTO.getUsername() + " created";
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable Long id) {
        return ResponseEntity.ok("User" + id + "blocked");
    }

    @PostMapping("/generateKey")
    public ResponseEntity<String> generateRandomKey(@PathVariable Long userId) {
        String key = generateRandomString();
        return ResponseEntity.ok(key);
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


