package com.kodilla.ecommercee.domain;


import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EntityOrderRepositoryMethodsTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    private User user;
    private Cart cart;

    @BeforeEach
    void setUp() {
        user = new User(
                "testUser", "password", "test@example.com",
                false
        );
        user = userRepository.save(user);

        cart = new Cart(
                null, user, BigDecimal.ZERO, null, null
        );
        cart = cartRepository.save(cart);
    }


    @Test
    void shouldSaveOrder() {
        // given
        Order order = new Order(LocalDateTime.now(), user, cart, BigDecimal.valueOf(100));

        // when
        Order savedOrder = orderRepository.save(order);

        // then
        assertNotNull(savedOrder.getId());
        Optional<Order> retrievedOrder = orderRepository.findById(savedOrder.getId());
        assertTrue(retrievedOrder.isPresent());
        assertEquals(order.getTotalPrice(), retrievedOrder.get().getTotalPrice());
        assertEquals(order.getUser().getId(), retrievedOrder.get().getUser().getId());
        assertEquals(order.getCart().getId(), retrievedOrder.get().getCart().getId());
        assertEquals(order.getDate(), retrievedOrder.get().getDate());

    }
    @Test
    void shouldReadOrder() {
        // given
        Order order = new Order(LocalDateTime.now(), user, cart, BigDecimal.valueOf(100));
        Order savedOrder = orderRepository.save(order);

        // when
        Optional<Order> readOrder = orderRepository.findById(savedOrder.getId());

        // then
        assertTrue(readOrder.isPresent());
        assertEquals(savedOrder.getId(), readOrder.get().getId());
    }
    @Test
    void shouldUpdateOrder() {
        // given
        Order order = new Order(LocalDateTime.now(), user, cart, BigDecimal.valueOf(100));
        user.getOrders().add(order);
        Order savedOrder = orderRepository.save(order);

        // when
        savedOrder.setDate(LocalDateTime.now().plusDays(1));
        savedOrder.setOrderValue(BigDecimal.valueOf(150));
        Order updatedOrder = orderRepository.save(savedOrder);

        // then
        assertEquals(savedOrder.getDate(), updatedOrder.getDate());
        assertEquals(savedOrder.getOrderValue(), updatedOrder.getOrderValue());

        User updatedUser = userRepository.findById(user.getId()).orElseThrow();
        boolean foundUpdatedOrder = updatedUser.getOrders().stream()
                .anyMatch(o -> o.getId().equals(updatedOrder.getId()) && o.getOrderValue().equals(BigDecimal.valueOf(150)));
        assertTrue(foundUpdatedOrder);

    }
    @Test
    void shouldDeleteOrder() {
        // given
        Order order = new Order(LocalDateTime.now(), user, cart, BigDecimal.valueOf(100));
        Order savedOrder = orderRepository.save(order);

        // when
        orderRepository.deleteById(savedOrder.getId());

        // then

        assertFalse(orderRepository.findById(savedOrder.getId()).isPresent());
        assertTrue(cartRepository.findById(cart.getId()).isPresent());

    }
}
