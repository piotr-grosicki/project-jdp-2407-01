package com.kodilla.ecommercee.domain.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartDto(
        Long id,
        Long userId,
        BigDecimal totalPrice,
        List<Long> productIds,
        Long orderId
) {
}
