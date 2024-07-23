package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.dto.CartDto;
import com.kodilla.ecommercee.domain.dto.OrderDto;
import com.kodilla.ecommercee.domain.dto.ProductDto;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ecommercee/carts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;

    @SneakyThrows
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCart(@RequestBody CartDto cartDto) {
        Cart cart = cartMapper.mapToCart(cartDto);
        cartService.saveCart(cart);
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @GetMapping("/products/{id}")
    public ResponseEntity<List<ProductDto>> getAllProductsFromCart(@PathVariable Long id) {
        List<Product> cartProducts = cartService.getCartProducts(id);
        return ResponseEntity.ok(productMapper.mapToProductDtoList(cartProducts));
    }

    @SneakyThrows
    @PatchMapping("/add/{cartId}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long cartId, @RequestParam Long productId) {
        Cart cart = cartService.addProduct(cartId, productId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }

    @SneakyThrows
    @PatchMapping("remove/{cartId}")
    public ResponseEntity<CartDto> removeProductFromCart(@PathVariable Long cartId, @RequestParam Long productId) {
        Cart cart = cartService.removeProduct(cartId, productId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }

    @SneakyThrows
    @PostMapping(value = "/createOrder/{cartId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> createOrderFromCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        Order orderFromCart = cartService.createOrderFromCart(cart);
        cart.setOrder(orderFromCart);
        cartService.saveCart(cart);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(orderFromCart));
    }

}
