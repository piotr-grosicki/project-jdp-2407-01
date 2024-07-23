package com.kodilla.ecommercee.domain.dto;

import java.math.BigDecimal;

public record OrderDto(
        Long orderId,
        Long userId,
        Long cartId,
        BigDecimal totalPrice
) {
}
