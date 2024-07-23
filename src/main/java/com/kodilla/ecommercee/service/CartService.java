package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.exception.*;
import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final OrderService orderService;

    public BigDecimal getCartTotalPrice(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Cart getCartById(Long id) throws CartNotFoundException {
        return cartRepository.findById(id).orElseThrow(CartNotFoundException::new);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public List<Product> getCartProducts(final Long id) throws CartNotFoundException {
        Cart cart = cartRepository.findById(id).orElseThrow(CartNotFoundException::new);
        return cart.getProducts();
    }

    public Cart addProduct(final Long cartId, final Long productId)
            throws ProductNotFoundException, CartNotFoundException, ProductAlreadyInCartException {
        Product product = productService.getProduct(productId);
        Cart cart = getCartById(cartId);
        if(cart.getProducts().contains(product))
            throw new ProductAlreadyInCartException();
        cart.getProducts().add(product);
        return cartRepository.save(cart);
    }

    public Cart removeProduct(final Long cartId, final Long productId)
            throws ProductNotFoundException, CartNotFoundException, ProductDoesNotBelongToCartException {
        Product product = productService.getProduct(productId);
        Cart cart = getCartById(cartId);
        if(!cart.getProducts().contains(product))
            throw new ProductDoesNotBelongToCartException();
        cart.getProducts().remove(product);
        return cartRepository.save(cart);
    }

    public Order createOrderFromCart(final Cart cart) throws EmptyCartException, CartWithOrderAlreadyException {
        if(cart.getProducts().isEmpty())
            throw new EmptyCartException();
        else if(cart.getOrder() != null)
            throw new CartWithOrderAlreadyException();
        return orderService.saveOrder(new Order(
                LocalDateTime.now(),
                cart.getUser(),
                cart,
                getCartTotalPrice(cart.getProducts())
                ));
    }
}