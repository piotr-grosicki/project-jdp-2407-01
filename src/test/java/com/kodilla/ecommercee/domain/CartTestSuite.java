package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CartTestSuite {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testCreateCart() {
        // Given
        Cart cart = new Cart();
        cart.setTotalPrice(BigDecimal.valueOf(100.00));

        // When
        Cart savedCart = cartRepository.save(cart);

        // Then
        Long id = savedCart.getId();
        Optional<Cart> createdCart = cartRepository.findById(id);
        assertThat(createdCart.isPresent()).isTrue();
    }

    @Test
    void testReadCart() {
        // Given
        Cart cart = new Cart();
        cart.setTotalPrice(BigDecimal.valueOf(200.00));
        cartRepository.save(cart);

        // When
        Long id = cart.getId();
        Optional<Cart> readCart = cartRepository.findById(id);

        // Then
        assertThat(readCart.isPresent()).isTrue();
        assertThat(readCart.get().getTotalPrice()).isEqualByComparingTo(BigDecimal.valueOf(200.00));
    }

    @Test
    void testUpdateCart() {
        // Given
        Cart cart = new Cart();
        cart.setTotalPrice(BigDecimal.valueOf(300.00));
        Cart saved = cartRepository.save(cart);
        Long id = cart.getId();
        saved.setTotalPrice(BigDecimal.valueOf(400.00));

        // When
        cartRepository.save(cart);

        // Then
        Optional<Cart> updatedCart = cartRepository.findById(id);
        assertThat(updatedCart.isPresent()).isTrue();
        assertThat(updatedCart.get().getTotalPrice()).isEqualByComparingTo(BigDecimal.valueOf(400.00));
    }

    @Test
    void testDeleteCart() {
        // Given
        Cart cart = new Cart();
        cart.setTotalPrice(BigDecimal.valueOf(500.00));
        User user = new User(null, "test username", "test password", "test e-mail", false, new ArrayList<>(), new ArrayList<>(), "test userKey", LocalDateTime.now());
        User savedUser = userRepository.save(user);
        cart.setUser(savedUser);
        Cart savedCart = cartRepository.save(cart);
        Long id = cart.getId();
        Long userId = savedCart.getUser().getId();

        // When
        cartRepository.deleteById(id);

        // Then
        Optional<Cart> deletedCart = cartRepository.findById(id);
        assertThat(deletedCart.isEmpty()).isTrue();
        assertTrue(userRepository.findById(userId).isPresent());
    }

}
