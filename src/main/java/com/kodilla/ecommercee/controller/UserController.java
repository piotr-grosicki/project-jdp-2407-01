package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.dto.UserDto;
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
        UserDto createdUserDto = userMapper.mapToCreateUserDto(createdUser);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @SneakyThrows
    @PatchMapping("/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable Long id) {
        Optional<User> user = userService.blockUser(id);
        if (user.isPresent()) {
            return new ResponseEntity<>("User " + id + " blocked", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @SneakyThrows
    @PostMapping("/{userId}/generateKey")
    public ResponseEntity<String> generateRandomKey(@PathVariable Long userId) {
        String key = userService.generateRandomKey(userId);
        return new ResponseEntity<>(key, HttpStatus.OK);
    }
}
