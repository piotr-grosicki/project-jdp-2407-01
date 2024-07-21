package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.dto.UserDto;
import com.kodilla.ecommercee.exception.user.UserNotFoundException;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/shop/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        User createdUser = userService.createUser(user);
        UserDto createdUserDto = userMapper.mapToUserDto(createdUser);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
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
    public ResponseEntity<UserDto> generateRandomKey(@PathVariable Long userId) {
        User user = userService.generateRandomKey(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }
}

