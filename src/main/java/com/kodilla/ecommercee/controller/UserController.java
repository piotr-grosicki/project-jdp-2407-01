package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/shop/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<User> blockUser(@PathVariable Long id) {
        Optional<User> userOpt = userService.blockUser(id);
        return userOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/generateKey")
    public ResponseEntity<String> generateRandomKey(@RequestParam String username, @RequestParam String password) {
        String key = userService.generateRandomKey(username, password);
        if (key != null) {
            return ResponseEntity.ok(key);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

}
