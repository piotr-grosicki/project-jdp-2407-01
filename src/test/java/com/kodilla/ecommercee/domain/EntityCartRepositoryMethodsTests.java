package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EntityCartRepositoryMethodsTests {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    private Cart cart;
    private User user;

    @BeforeEach
    void setUp() {
        User user = new User("test name", "test password", "test mail", false);
        user = userRepository.save(user);
        cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(BigDecimal.valueOf(100.00));
    }

    @Test
    void testCreateCart() {
        // Given
        // When
        Cart savedCart = cartRepository.save(cart);

        // Then
        Long id = savedCart.getId();
        Optional<Cart> createdCart = cartRepository.findById(id);
        assertTrue(createdCart.isPresent());
    }

    @Test
    void testReadCart() {
        // Given
        cartRepository.save(cart);
        Long id = cart.getId();

        // When
        List<Cart> allCarts = cartRepository.findAll();
        Optional<Cart> readCart = cartRepository.findById(id);

        // Then
        assertTrue(readCart.isPresent());
        assertEquals(readCart.get().getTotalPrice(), BigDecimal.valueOf(100.00));
        assertEquals(1, allCarts.size());
    }

    @Test
    void testUpdateCart() {
        // Given
        Cart saved = cartRepository.save(cart);
        Product product = new Product("Airmax", BigDecimal.valueOf(100), "testing", new Group());
        saved.setTotalPrice(BigDecimal.valueOf(400.00));
        saved.getProducts().add(product);

        // When
        cartRepository.save(saved);

        // Then
        Long id = saved.getId();
        Optional<Cart> updatedCart = cartRepository.findById(id);
        assertTrue(updatedCart.isPresent());
        assertEquals(BigDecimal.valueOf(400.00), updatedCart.get().getTotalPrice());
        assertEquals(1, updatedCart.get().getProducts().size());
    }

    @Test
    void testDeleteCart() {
        // Given
        Order order = new Order(LocalDateTime.now(), user, cart, BigDecimal.valueOf(100));
        cart.setOrder(order);
        Cart savedCart = cartRepository.save(cart);
        Long id = cart.getId();
        Long userId = savedCart.getUser().getId();
        Long orderId = savedCart.getOrder().getId();

        // When
        cartRepository.deleteById(id);

        // Then
        Optional<Cart> deletedCart = cartRepository.findById(id);
        assertTrue(deletedCart.isEmpty());
        assertTrue(userRepository.findById(userId).isPresent());
        assertTrue(orderRepository.findById(orderId).isPresent());
    }
}