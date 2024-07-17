package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EntityProductRepositoryMethodsTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CartRepository cartRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        Group group = new Group();
        product = new Product(
                null,
                "test name",
                new BigDecimal(120),
                "just an object for test",
                group,
                new ArrayList<>()
        );
        groupRepository.save(group);
    }

    @Test
    public void shouldCreateProduct() {
        //Given
        Long groupBeforeSave = product.getGroup().getId();
        //When
        Product savedProduct = productRepository.save(product);

        //Then
        Long productId = savedProduct.getId();
        Long groupId = savedProduct.getGroup().getId();
        assertTrue(productRepository.findById(productId).isPresent());
        assertEquals(groupBeforeSave, groupId);
    }

    @Test
    public void shouldReadProducts() {
        //Given
        Product savedProduct = productRepository.save(product);
        Long productId = savedProduct.getId();

        //When
        Optional<Product> productFoundById = productRepository.findById(productId);
        List<Product> allFoundProducts = productRepository.findAll();

        //Then
        assertTrue(productFoundById.isPresent());
        assertEquals(productFoundById.get().getId(), productId);
        assertTrue(allFoundProducts.contains(savedProduct));
        assertEquals(1, allFoundProducts.size());
    }

    @Test
    public void shouldUpdateProduct() {
        //Given
        Cart cart = new Cart();
        product.getCarts().add(cart);
        //When
        Product savedProduct = productRepository.save(product);

        //Then
        Long cartId = cart.getId();
        assertEquals(1, savedProduct.getCarts().size());
        assertNotNull(savedProduct.getCarts().get(0));
        assertTrue(cartRepository.findById(cartId).isPresent());
    }

    @Test
    public void shouldDeleteProduct() {
        //Given
        product.getCarts().add(new Cart());
        Product savedProduct = productRepository.save(product);
        Long productId = savedProduct.getId();
        Long cartId = savedProduct.getCarts().get(0).getId();
        Long groupId = savedProduct.getGroup().getId();
        List<Product> allProducts = productRepository.findAll();

        //When
        productRepository.delete(savedProduct);

        //Then
        Optional<Product> productFoundById = productRepository.findById(productId);
        assertTrue(productFoundById.isEmpty());
        assertEquals(1, allProducts.size());
        assertEquals(0, productRepository.findAll().size());
        assertTrue(cartRepository.findById(cartId).isPresent());
        assertTrue(groupRepository.findById(groupId).isPresent());
    }
}