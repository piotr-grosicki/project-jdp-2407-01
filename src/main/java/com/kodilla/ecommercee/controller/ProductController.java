package com.kodilla.ecommercee.controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/ecommercee/products")
public class ProductController {

    @GetMapping
    public List<Object> getProducts() {
        return new ArrayList<>();
    }

    @GetMapping
    public Object getProduct(Long productId) {
        return new Object();
    }

    @PostMapping
    public void createProduct(Object productDto) {
    }

    @PutMapping
    public Object updateProduct(Object productDto) {
        return new Object();
    }

    @DeleteMapping
    public void deleteProduct(Long productId) {
    }
}
