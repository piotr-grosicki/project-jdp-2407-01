package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.exception.CartNotFoundException;
import com.kodilla.ecommercee.controller.exception.ProductNotFoundException;
import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {


    private CartRepository cartRepository;


    private ProductRepository productRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    public boolean existsCart(Long id) {
        return cartRepository.existsById(id);
    }

    @SneakyThrows
    public Cart addProductToCart(Long cartId, Long productId) throws CartNotFoundException {
        Cart cart = getCartById(cartId).orElseThrow(CartNotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        cart.getProducts().add(product);
        return cartRepository.save(cart);
    }

    @SneakyThrows
    public Cart removeProductFromCart(Long cartId, Long productId) throws CartNotFoundException {
        Cart cart = getCartById(cartId).orElseThrow(CartNotFoundException::new);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        cart.getProducts().remove(product);
        return cartRepository.save(cart);
    }
}