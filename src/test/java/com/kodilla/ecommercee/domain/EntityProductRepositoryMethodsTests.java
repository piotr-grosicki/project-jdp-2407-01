package com.kodilla.ecommercee.domain;

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

    private Product product;

    @BeforeEach
    void setUp() {
        Group group = new Group();
        groupRepository.save(group);
        product = new Product(
                null,
                "test name",
                new BigDecimal(120),
                "just an object for test",
                group,
                new ArrayList<>()
        );
    }

    @Test
    public void shouldCreateProduct() {
        //Given
        //When
        productRepository.save(product);

        //Then
        Long id = product.getId();
        assertTrue(productRepository.findById(id).isPresent());
    }

    @Test
    public void shouldReadProducts() {
        //Given
        Product saved = productRepository.save(product);
        Long productId = saved.getId();

        //When
        Optional<Product> productFoundById = productRepository.findById(productId);
        List<Product> allFoundProducts = productRepository.findAll();

        //Then
        assertTrue(productFoundById.isPresent());
        assertEquals(productFoundById.get().getId(), productId);
        assertTrue(allFoundProducts.contains(saved));
        assertEquals(1, allFoundProducts.size());
    }

    @Test
    public void shouldUpdateProduct() {
        //Given
        product.getCarts().add(new Cart());
        //When
        productRepository.save(product);

        //Then
        assertEquals(1, product.getCarts().size());
        assertNotNull(product.getCarts().get(0));
    }

    @Test
    public void shouldDeleteProduct() {
        //Given
        productRepository.save(product);
        Long productId = product.getId();
        List<Product> allProducts = productRepository.findAll();

        //When
        productRepository.delete(product);

        //Then
        Optional<Product> productFoundById = productRepository.findById(productId);
        assertTrue(productFoundById.isEmpty());
        assertEquals(1, allProducts.size());
        assertEquals(0, productRepository.findAll().size());
    }
}