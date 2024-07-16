package com.kodilla.ecommercee.domain;


import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class OrderTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    private User testUser;
    private Cart testCart;

    @BeforeEach
    void setUp() {

        testUser = new User(null, "testUser", "password", "test@example.com", false, null, null, null, null);
        userRepository.save(testUser);


        testCart = new Cart(null, testUser, null);
        cartRepository.save(testCart);
    }


    @Test
    void SavedOrder() {
        // given
        Order order = new Order(null, LocalDateTime.now(), testUser, testCart, BigDecimal.valueOf(100));

        // when
        Order savedOrder = orderRepository.save(order);

        // then
        assertNotNull(savedOrder.getId());
    }
    @Test
    void ReadOrder() {
        // given
        Order order = new Order(null, LocalDateTime.now(), testUser, testCart, BigDecimal.valueOf(100));
        Order savedOrder = orderRepository.save(order);

        // when
        Optional<Order> readOrder = orderRepository.findById(savedOrder.getId());

        // then
        assertTrue(readOrder.isPresent());
        assertEquals(savedOrder.getId(), readOrder.get().getId());
    }
    @Test
    void updateOrder() {
        // given
        Order order = new Order(null, LocalDateTime.now(), testUser, testCart, BigDecimal.valueOf(100));
        Order savedOrder = orderRepository.save(order);

        // when
        savedOrder.setDate(LocalDateTime.now().plusDays(1));
        savedOrder.setOrderValue(BigDecimal.valueOf(150));
        Order updatedOrder = orderRepository.save(savedOrder);

        // then
        assertEquals(savedOrder.getDate(), updatedOrder.getDate());
        assertEquals(savedOrder.getOrderValue(), updatedOrder.getOrderValue());
    }
    @Test
    void deleteOrder() {
        // given
        Order order = new Order(null, LocalDateTime.now(), testUser, testCart, BigDecimal.valueOf(100));
        Order savedOrder = orderRepository.save(order);

        // when
        orderRepository.deleteById(savedOrder.getId());

        // then
        Optional<Order> readOrder = orderRepository.findById(savedOrder.getId());
        assertFalse(readOrder.isPresent());
    }
}
