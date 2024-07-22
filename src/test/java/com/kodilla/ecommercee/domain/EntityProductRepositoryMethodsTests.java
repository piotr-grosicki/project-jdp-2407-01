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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        product = new Product(
                "test name",
                new BigDecimal(120),
                "just an object for test",
                group
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
        boolean existsByName = productRepository.existsByName("test name");

        //Then
        assertTrue(productFoundById.isPresent());
        assertEquals(productFoundById.get().getId(), productId);
        assertTrue(allFoundProducts.contains(savedProduct));
        assertEquals(1, allFoundProducts.size());
        assertTrue(existsByName);
    }

    @Test
    public void shouldUpdateProduct() {
        //Given
        productRepository.save(product);
        Long productId = product.getId();
        product.setName("updated name");
        //When
        productRepository.save(product);
        //Then
        assertNotEquals(productRepository.findById(productId).get().getName(), "test name");
        assertEquals("updated name", productRepository.findById(productId).get().getName());
    }

    @Test
    public void shouldDeleteProduct() {
        //Given
        Product savedProduct = productRepository.save(product);
        Long productId = savedProduct.getId();
        Long groupId = savedProduct.getGroup().getId();
        List<Product> allProducts = productRepository.findAll();

        //When
        productRepository.delete(savedProduct);

        //Then
        assertTrue(productRepository.findById(productId).isEmpty());
        assertEquals(1, allProducts.size());
        assertEquals(0, productRepository.findAll().size());
        assertTrue(groupRepository.findById(groupId).isPresent());
    }
}