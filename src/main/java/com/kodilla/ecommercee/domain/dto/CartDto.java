package com.kodilla.ecommercee.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CartDto {
    private String id;
    private List<String> items;

    public CartDto() {}

    public CartDto(String id, List<String> items) {
        this.id = id;
        this.items = items;
    }

}
