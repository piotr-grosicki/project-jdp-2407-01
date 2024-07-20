package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.dto.CartDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/shop/carts")
public class CartController {

    private final List<CartDto> carts = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts() {
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartById(@PathVariable Long id) {
        return carts.stream()
                .filter(cart -> cart.id().equals(id))
                .findFirst()
                .map(cart -> new ResponseEntity<>(cart, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto) {
        carts.add(cartDto);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDto> updateCart(@PathVariable Long id, @RequestBody CartDto updatedCart) {
        for (CartDto cart : carts) {
            if (cart.id().equals(id)) {
                carts.remove(cart);
                carts.add(updatedCart);
                return new ResponseEntity<>(updatedCart, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        return carts.removeIf(cart -> cart.id().equals(id))
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
