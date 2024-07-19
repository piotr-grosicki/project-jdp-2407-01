package com.kodilla.ecommercee.domain.dto;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        String description,
        Long groupId
) {
}
