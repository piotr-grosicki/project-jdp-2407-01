package com.kodilla.ecommercee.mapper;
import com.kodilla.ecommercee.controller.exception.OrderNotFoundException;
import com.kodilla.ecommercee.controller.exception.UserNotFoundException;
import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.dto.CartDto;
import com.kodilla.ecommercee.service.OrderService;
import com.kodilla.ecommercee.service.ProductService;
import com.kodilla.ecommercee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartMapper {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ProductService productService;

    @Autowired
    private final OrderService orderService;

    public Cart mapToCart(final CartDto cartDto) throws UserNotFoundException, OrderNotFoundException {
        return new CartDto(
                cartDto.getId(),
                userService.getUser(cartDto.getUserId()),
                orderService.getOrder(cartDto.getOrderId()),
                cartDto.getProductIds().stream()
                        .map(productService::getProduct)
                        .collect(Collectors.toList())
        );
    }

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.getUser().getId(),
                cart.getTotalPrice(),
                cart.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toList()),
                cart.getOrder().getId()
        );
    }

    public List<Cart> mapToCartList(final List<CartDto> cartDtoList) {
        return cartDtoList.stream()
                .map(c -> {
                    try {
                        return mapToCart(c);
                    } catch (UserNotFoundException | OrderNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    public List<CartDto> mapToCartDtoList(final List<Cart> cartList) {
        return cartList.stream()
                .map(this::mapToCartDto).collect(Collectors.toList());
    }
}