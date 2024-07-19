package com.kodilla.ecommercee.domain.dto;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Long groupId
) {
}
