package com.kodilla.ecommercee.controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/ecommercee/products")
public class ProductController {

    @GetMapping
    public List<String> getProducts() {
        return new ArrayList<>();
    }

    @GetMapping("/product")
    public String getProduct(Long productId) {
        return "product";
    }

    @PostMapping
    public void createProduct(String productDto) {
    }

    @PutMapping
    public String updateProduct(String productDto) {
        return "updated product";
    }

    @DeleteMapping
    public void deleteProduct(Long productId) {
    }
}
