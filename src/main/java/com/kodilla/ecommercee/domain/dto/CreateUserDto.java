package com.kodilla.ecommercee.domain.dto;

public record CreateUserDto(
        Long id,
        String username,
        String password,
        String email
) {
}
