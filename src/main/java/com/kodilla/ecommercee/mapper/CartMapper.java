package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.dto.CartDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public Cart mapToCart(CartDto cartDto, User user, List<Product> products, Order order) {
        return new Cart(
                        cartDto.id(),
                        user,
                        cartDto.totalPrice(),
                        order,
                        products
                         );
    }

    public CartDto mapToCartDto(Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.getUser().getId(),
                cart.getTotalPrice(),
                cart.getProducts().stream().map(Product::getId).collect(Collectors.toList()),
                cart.getOrder().getId()
        );
    }

    public Cart mapToCart(CartDto cartDto) {
        return null;
    }
}