package com.kodilla.ecommercee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GroupDto {
    private Long id;
    private String name;
    private List<ProductDto> products;
}
