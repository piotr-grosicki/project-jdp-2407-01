package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.controller.exception.UserAlreadyExistsException;
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
@RequestMapping("/v1/ecommercee/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        if(userService.existsByUsername(userDto.username())) {
            throw new UserAlreadyExistsException();
        }
        User user = userMapper.mapToUser(userDto);
        User createdUser = userService.saveUser(user);
        return new ResponseEntity<>(userMapper.mapToUserDto(createdUser), HttpStatus.CREATED);
    }

    @SneakyThrows
    @PatchMapping("/{id}/block")
    public ResponseEntity<UserDto> blockUser(@PathVariable Long id) {
        User userById = userService.getUserById(id);
        userById.setBlocked(true);
        return ResponseEntity.ok(userMapper.mapToUserDto(userService.saveUser(userById)));
    }

    @SneakyThrows
    @PutMapping("/{userId}/generateKey")
    public ResponseEntity<UserDto> generateRandomKey(
            @PathVariable Long userId,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {
        User user = userService.generateRandomKey(userId, username, email, password);
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }
    @SneakyThrows
    @PutMapping("/{id}/update")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User mappedUser = userMapper.mapToUser(userDto);
        User savedUser = userService.saveUser(mappedUser);
        return ResponseEntity.ok(userMapper.mapToUserDto(savedUser));
    }
}

