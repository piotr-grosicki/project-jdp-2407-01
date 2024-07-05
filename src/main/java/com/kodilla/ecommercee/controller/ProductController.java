package com.kodilla.ecommercee.controller;
import com.kodilla.ecommercee.domain.dto.ProductDto;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/ecommercee/products")
public class ProductController {

    @GetMapping
    public List<ProductDto> getProducts() {
        return new ArrayList<>();
    }

    @GetMapping("/product")
    public ProductDto getProduct(Long productId) {
        return new ProductDto(
                1L,
                "test_name",
                "test_description",
                new BigDecimal(1),
                1L
        );
    }

    @PostMapping
    public void createProduct(ProductDto productDto) {
    }

    @PutMapping
    public ProductDto updateProduct(ProductDto productDto) {
        return new ProductDto(
                1L,
                "updated_test_name",
                "updated_test_description",
                new BigDecimal(1),
                1L
        );
    }

    @DeleteMapping
    public void deleteProduct(Long productId) {
    }
}
