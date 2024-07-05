package com.kodilla.ecommercee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("v1/shop/users")
public class UserController {

    private final Random random = new Random();

    @PostMapping
    public ResponseEntity<String> createUser() {

        return ResponseEntity.ok("createdUser");
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable Long id) {
        return ResponseEntity.ok("User" + id + "blocked");
    }

    @PostMapping("/generateKey")
    public ResponseEntity<String> generateRandomKey(@RequestParam String username, @RequestParam String password) {
        String key = generateRandomString();
        return ResponseEntity.ok(key);
        }

    private String generateRandomString() {
        String characters = "ABCDEFGHIJ0123456789";
        StringBuilder sb = new StringBuilder(24);
        for (int i = 0; i < 24; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}


