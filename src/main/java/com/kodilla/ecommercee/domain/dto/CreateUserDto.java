package com.kodilla.ecommercee.domain.dto;

public record CreateUserDto(
        String username,
        String password,
        String email
) {
}
