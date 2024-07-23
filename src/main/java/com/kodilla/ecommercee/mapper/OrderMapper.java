package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.controller.exception.CartNotFoundException;
import com.kodilla.ecommercee.controller.exception.UserNotFoundException;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.dto.OrderDto;
import com.kodilla.ecommercee.service.CartService;
import com.kodilla.ecommercee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderMapper {

    private final UserService userService;
    private final CartService cartService;

    public OrderDto mapToOrderDto(final Order order) {
        return new OrderDto(
                order.getId(),
                order.getUser().getId(),
                order.getCart().getId(),
                order.getTotalPrice());
    }

    public Order mapToOrder(final OrderDto orderDto) throws CartNotFoundException, UserNotFoundException {
        return new Order(
                LocalDateTime.now(),
                userService.getUserById(orderDto.userId()),
                cartService.getCartById(orderDto.cartId()),
                orderDto.totalPrice()
        );
    }
}
