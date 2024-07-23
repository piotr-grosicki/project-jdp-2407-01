package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.controller.exception.CartNotFoundException;
import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.dto.CartDto;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/ecommercee/carts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto) {
        Cart cart = cartMapper.mapToCart(cartDto);
        Cart createdCart = cartService.saveCart(cart);
        return new ResponseEntity<>(cartMapper.mapToCartDto(createdCart), HttpStatus.CREATED);
    }

    @SneakyThrows
    @PatchMapping("/{id}/addProduct/{productId}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long id, @PathVariable Long productId) {
        if (!cartService.existsCart(id)) {
            throw new CartNotFoundException();
        }
        Cart updatedCart = cartService.addProductToCart(id, productId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(updatedCart));
    }

    @SneakyThrows
    @PatchMapping("/{id}/removeProduct/{productId}")
    public ResponseEntity<CartDto> removeProductFromCart(@PathVariable Long id, @PathVariable Long productId) {
        if (!cartService.existsCart(id)) {
            throw new CartNotFoundException();
        }
        Cart updatedCart = cartService.removeProductFromCart(id, productId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(updatedCart));
    }

    @SneakyThrows
    @PutMapping("/{id}/update")
    public ResponseEntity<CartDto> updateCart(@PathVariable Long id, @RequestBody CartDto cartDto) {
        if (!cartService.existsCart(id)) {
            throw new CartNotFoundException();
        }
        Cart cart = cartMapper.mapToCart(cartDto);
        cart.setId(id);
        Cart updatedCart = cartService.saveCart(cart);
        return ResponseEntity.ok(cartMapper.mapToCartDto(updatedCart));
    }
}