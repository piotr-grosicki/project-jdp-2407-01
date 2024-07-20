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
                LocalDateTime.now(), // Using the current date and time for the new order
                getDefaultUser(),    // Default User object
                getDefaultCart(),    // Default Cart object
                orderDto.value()
        );
    }

    /**
     * Converts an Order entity to an OrderDto.
     *
     * @param order the Order entity to be converted
     * @return the converted OrderDto
     */
    public OrderDto mapToOrderDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getTotalPrice()
        );
    }

    /**
     * Provides a default User object. This is just an example; adjust as needed.
     *
     * @return a default User object
     */
    private User getDefaultUser() {
        // Here you would typically fetch a default or placeholder user from a service or repository
        // For demonstration purposes, we return a new User instance with an ID
        User defaultUser = new User();
        defaultUser.setId(1L); // Set default ID or any other required fields
        return defaultUser;
    }

    /**
     * Provides a default Cart object. This is just an example; adjust as needed.
     *
     * @return a default Cart object
     */
    private Cart getDefaultCart() {
        // Here you would typically fetch a default or placeholder cart from a service or repository
        // For demonstration purposes, we return a new Cart instance with an ID
        Cart defaultCart = new Cart();
        defaultCart.setId(1L); // Set default ID or any other required fields
        return defaultCart;
    }
}