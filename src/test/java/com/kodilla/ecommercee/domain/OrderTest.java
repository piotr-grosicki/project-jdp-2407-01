package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

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

    private User testUser;

    @BeforeEach
    void setup() {
        testUser = new User(null, "testUser", "password", "test@example.com", false, null, null, null, null);
        userRepository.save(testUser);
    }

    @AfterEach
    void cleanup() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void newOrder() {
        // given
        Order order = new Order(null, LocalDateTime.now(), testUser);

        // when
        Order savedOrder = orderRepository.save(order);

        // then
        assertNotNull(savedOrder.getId());
    }

    @Test
    void savedOrder() {
        // given
        Order order = new Order(null, LocalDateTime.now(), testUser);
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
        Order order = new Order(null, LocalDateTime.now(), testUser);
        Order savedOrder = orderRepository.save(order);

        // when
        savedOrder.setDate(LocalDateTime.now().plusDays(1));
        Order updatedOrder = orderRepository.save(savedOrder);

        // then
        assertEquals(savedOrder.getDate(), updatedOrder.getDate());
    }

    @Test
    void deleteOrder() {
        // given
        Order order = new Order(null, LocalDateTime.now(), testUser);
        Order savedOrder = orderRepository.save(order);

        // when
        orderRepository.deleteById(savedOrder.getId());

        // then
        Optional<Order> readOrder = orderRepository.findById(savedOrder.getId());
        assertFalse(readOrder.isPresent());
    }
}