package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EntityUserRepositoryMethodsTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
                "macro24", "password", "macro24@gmail.com",
                false
        );
    }

    @Test
    public void shouldCreateUser() {
        //Given
        //When
        User savedUser = userRepository.save(user);
        //Then
        Long id = savedUser.getId();
        String username = savedUser.getUsername();
        assertTrue(userRepository.findById(id).isPresent());
        assertEquals("macro24", username);
    }

    @Test
    public void shouldReadUser() {
        //Given
        User saved = userRepository.save(user);
        Long id = saved.getId();
        //When
        List<User> allUsers = userRepository.findAll();
        Optional<User> userById = userRepository.findById(id);
        //Then
        assertTrue(userById.isPresent());
        assertEquals(id, userById.get().getId());
        assertEquals("macro24", userById.get().getUsername());
        assertEquals("password", userById.get().getPassword());
        assertTrue(allUsers.contains(saved));
        assertEquals(1, allUsers.size());
    }

    @Test
    public void shouldUpdateUser() {
        //Given
        String newPassword = "newPassword";
        user.setBlocked(true);
        user.setPassword(newPassword);
        //When
        User savedUser = userRepository.save(user);
        //Then
        assertTrue(orderRepository.findAll().isEmpty());
        assertTrue(cartRepository.findAll().isEmpty());
        assertEquals("newPassword", savedUser.getPassword());
        assertTrue(savedUser.isBlocked());
    }

    @Test
    public void shouldDeleteUser() {
        //Given
        Order order = new Order(LocalDateTime.now(), user);
        user.getOrders().add(order);
        userRepository.save(user);
        Long userId = user.getId();
        Long orderId = order.getId();
        //When
        userRepository.deleteById(userId);
        //Then
        assertFalse(userRepository.findById(userId).isPresent());
        assertTrue(orderRepository.findById(orderId).isPresent());
    }
}