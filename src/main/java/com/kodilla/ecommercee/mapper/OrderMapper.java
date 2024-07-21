package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderMapper {
    public Order mapToOrder(OrderDto orderDto) {
        return new Order(
                LocalDateTime.now(),
                getDefaultUser(),
                getDefaultCart(),
                orderDto.value()
        );
    }


    public OrderDto mapToOrderDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getTotalPrice()
        );
    }


    private User getDefaultUser() {

        User defaultUser = new User();
        defaultUser.setId(1L);
        return defaultUser;
    }


    private Cart getDefaultCart() {

        Cart defaultCart = new Cart();
        defaultCart.setId(1L);
        return defaultCart;
    }
}