package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.dto.CreateUserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(CreateUserDto createUserDto) {
        return new User(
                createUserDto.username(),
                createUserDto.password(),
                createUserDto.email(),
                false
        );
    }

    public CreateUserDto mapToCreateUserDto(User user) {
        return new CreateUserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail()
        );
    }
}
