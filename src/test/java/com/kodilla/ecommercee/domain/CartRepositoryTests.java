package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateCart() {
        // Tworzenie użytkownika
        User user = new User();
        user.setNickname("TestUser");
        userRepository.save(user);

        // Tworzenie koszyka
        Cart cart = new Cart();
        cart.setTotalPrice(BigDecimal.valueOf(100.00));
        cart.setUser(user);
        cartRepository.save(cart);

        // Sprawdzanie, czy koszyk został zapisany
        Optional<Cart> foundCart = cartRepository.findById(cart.getId());
        assertThat(foundCart).isPresent();
        assertThat(foundCart.get().getTotalPrice()).isEqualTo(BigDecimal.valueOf(100.00));
    }

    @Test
    public void testReadCart() {
        // Tworzenie użytkownika
        User user = new User();
        user.setNickname("TestUser");
        userRepository.save(user);

        // Tworzenie koszyka
        Cart cart = new Cart();
        cart.setTotalPrice(BigDecimal.valueOf(100.00));
        cart.setUser(user);
        cartRepository.save(cart);

        // Odczytywanie koszyka
        Optional<Cart> foundCart = cartRepository.findById(cart.getId());
        assertThat(foundCart).isPresent();
        assertThat(foundCart.get().getTotalPrice()).isEqualTo(BigDecimal.valueOf(100.00));
    }

    @Test
    public void testUpdateCart() {
        // Tworzenie użytkownika
        User user = new User();
        user.setNickname("TestUser");
        userRepository.save(user);

        // Tworzenie koszyka
        Cart cart = new Cart();
        cart.setTotalPrice(BigDecimal.valueOf(100.00));
        cart.setUser(user);
        cartRepository.save(cart);

        // Aktualizacja koszyka
        cart.setTotalPrice(BigDecimal.valueOf(200.00));
        cartRepository.save(cart);

        // Sprawdzanie, czy koszyk został zaktualizowany
        Optional<Cart> foundCart = cartRepository.findById(cart.getId());
        assertThat(foundCart).isPresent();
        assertThat(foundCart.get().getTotalPrice()).isEqualTo(BigDecimal.valueOf(200.00));
    }

    @Test
    public void testDeleteCart() {
        // Tworzenie użytkownika
        User user = new User();
        user.setNickname("TestUser");
        userRepository.save(user);

        // Tworzenie koszyka
        Cart cart = new Cart();
        cart.setTotalPrice(BigDecimal.valueOf(100.00));
        cart.setUser(user);
        cartRepository.save(cart);

        // Usuwanie koszyka
        cartRepository.delete(cart);

        // Sprawdzanie, czy koszyk został usunięty
        Optional<Cart> foundCart = cartRepository.findById(cart.getId());
        assertThat(foundCart).isNotPresent();
    }
}
