package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.dto.UserDto;
import com.kodilla.ecommercee.controller.exception.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/shop/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto UserDto) {
        User user = userMapper.mapToUser(UserDto);
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(userMapper.mapToUserDto(createdUser), HttpStatus.CREATED);
    }

    @SneakyThrows
    @PatchMapping("/{id}/block")
    public ResponseEntity<UserDto> blockUser(@PathVariable Long id) {
        User user = userService.blockUser(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }

    @SneakyThrows
    @PutMapping("/{userId}/generateKey")
    public ResponseEntity<UserDto> generateRandomKey(
            @PathVariable Long userId,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {
        User user = userService.generateRandomKey(userId, username, email, password)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found or validation failed"));
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok(userMapper.mapToUserDto(updatedUser));
    }
}

