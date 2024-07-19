package com.kodilla.ecommercee.domain.dto;

import java.util.List;

public record GroupDto(
        Long id,
        String name,
        List<Long> products
) {
}
