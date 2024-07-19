package com.kodilla.ecommercee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class CartDto {
    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private List<ProductDto> productIds;
    private Long orderId;
}