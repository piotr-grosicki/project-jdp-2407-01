package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(UserDto userDto) {
        return new User(
                userDto.id(),
                userDto.username(),
                userDto.password(),
                userDto.email(),
                userDto.isBlocked()
        );
    }

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.isBlocked()
        );
    }

    public void updateUserFromDto(UserDto userDto, User user) {
        if (userDto.username() != null) user.setUsername(userDto.username());
        if (userDto.password() != null) user.setPassword(userDto.password());
        if (userDto.email() != null) user.setEmail(userDto.email());

    }
}
