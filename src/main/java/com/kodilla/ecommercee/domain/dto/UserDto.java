package com.kodilla.ecommercee.domain.dto;

public record UserDto(
        Long id,
        String username,
        String password,
        String email,
        boolean isBlocked
) {
}
