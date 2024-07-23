package com.kodilla.ecommercee.mapper;
import com.kodilla.ecommercee.controller.exception.CartNotFoundException;
import com.kodilla.ecommercee.controller.exception.OrderNotFoundException;
import com.kodilla.ecommercee.controller.exception.UserNotFoundException;
import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.dto.CartDto;
import com.kodilla.ecommercee.service.CartService;
import com.kodilla.ecommercee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartMapper {

    private final UserService userService;
    private final CartService cartService;

    public Cart mapToCart(final CartDto cartDto) throws UserNotFoundException, OrderNotFoundException, CartNotFoundException {
        BigDecimal totalPrice = cartDto.totalPrice() != null ? cartDto.totalPrice() : BigDecimal.ZERO;
        List<Product> products = cartDto.id() == null ? new ArrayList<>() : cartService.getCartProducts(cartDto.id());
        return new Cart(
                userService.getUserById(cartDto.userId()),
                totalPrice,
                products
        );
    }

    public CartDto mapToCartDto(final Cart cart) {
        BigDecimal totalPrice = cart.getTotalPrice() != null ? cartService.getCartTotalPrice(cart.getProducts()) : BigDecimal.ZERO;
        List<Long> productsId = cart.getProducts().isEmpty() ? new ArrayList<>() : cart.getProducts().stream()
                .map(Product::getId)
                .toList();

        return new CartDto(
                cart.getId(),
                cart.getUser().getId(),
                totalPrice,
                productsId
        );
    }
}