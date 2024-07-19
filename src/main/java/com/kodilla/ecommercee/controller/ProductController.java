package com.kodilla.ecommercee.controller;
import com.kodilla.ecommercee.controller.exception.ProductAlreadyExistsException;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.dto.ProductDto;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ecommercee/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();
        return ResponseEntity.ok(productMapper.mapToProductDtoList(allProducts));
    }

    @SneakyThrows
    @GetMapping(value = "{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productMapper.mapToProductDto(productService.getProduct(productId)));
    }

    @SneakyThrows
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createProduct(@RequestBody ProductDto productDto) {
        if (productService.existsProduct(productDto.name())) {
            throw new ProductAlreadyExistsException();
        } else {
            Product product = productMapper.mapToProduct(productDto);
            productService.saveProduct(product);
        }
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PutMapping(value = "/update")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        Product product = productMapper.mapToProduct(productDto);
        Product updatedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(productMapper.mapToProductDto(updatedProduct));
    }

    @SneakyThrows
    @DeleteMapping(value = "{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        Product product = productService.getProduct(productId);
        productService.deleteProduct(product);
        return ResponseEntity.ok().build();
    }
}
