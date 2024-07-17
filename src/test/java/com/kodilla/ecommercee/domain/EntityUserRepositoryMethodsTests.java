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

@ExtendWith(SpringExtension.class)
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
                null, "macro24", "password", "macro24@gmail.com",
                false, new ArrayList<>(), new ArrayList<>(),
                "1234ABCD", LocalDateTime.now()
        );
    }

    @Test
    public void shouldCreateUser() {
        //Given
        Cart cart = new Cart();
        cart.setUser(user);
        user.getCarts().add(cart);
        //When
        User savedUser = userRepository.save(user);
        //Then
        Long id = savedUser.getId();
        String userKey = savedUser.getUserKey();
        assertTrue(userRepository.findById(id).isPresent());
        assertTrue(cartRepository.findById(savedUser.getCarts().get(0).getId()).isPresent());
        assertEquals("1234ABCD", userKey);
    }

    @Test
    public void shouldReadUser() {
        //Given
        User saved = userRepository.save(user);
        Long id = user.getId();
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
        Cart cart = new Cart();
        cart.setUser(user);
        Order order = new Order();
        order.setUser(user);
        String newPassword = "newPassword";
        user.setBlocked(true);
        user.setPassword(newPassword);
        user.getOrders().add(order);
        user.getCarts().add(cart);
        //When
        User savedUser = userRepository.save(user);
        //Then
        Long orderId = user.getOrders().get(0).getId();
        Optional<Order> orderById = orderRepository.findById(orderId);
        assertTrue(orderById.isPresent());
        assertEquals("newPassword", savedUser.getPassword());
        assertEquals(1, savedUser.getOrders().size());
        assertEquals(1, savedUser.getCarts().size());
        assertTrue(savedUser.isBlocked());
    }

    @Test
    public void shouldDeleteUser() {
        //Given
        Order order = new Order();
        order.setUser(user);
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