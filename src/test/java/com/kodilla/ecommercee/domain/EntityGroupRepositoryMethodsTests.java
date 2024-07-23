package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.service.GroupService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import java.util.ArrayList;


import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EntityGroupRepositoryMethodsTests {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GroupService groupService;

    private Group group;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        groupRepository.deleteAll();

        group = new Group();
        group.setName("Electronics");
        groupRepository.save(group);
    }

    @Test
    public void shouldCreateGroup() {
        // Given
        Group newGroup = new Group();
        newGroup.setName("Toys");

        // When
        Group savedGroup = groupRepository.save(newGroup);
        Optional<Group> foundGroup = groupRepository.findById(savedGroup.getId());

        // Then
        assertNotNull(savedGroup.getId());
        assertEquals("Toys", savedGroup.getName());
        assertTrue(foundGroup.isPresent());
    }

    @Test
    public void shouldReadGroup() {
        // Given
        Long groupId = group.getId();

        // When
        Optional<Group> foundGroup = groupRepository.findById(groupId);
        List<Group> allGroups = groupRepository.findAll();

        // Then
        assertTrue(foundGroup.isPresent(), "Group should be present");
        assertEquals(groupId, foundGroup.get().getId());
        assertTrue(allGroups.contains(group));
        assertEquals(1, allGroups.size());
    }

    @Test
    public void shouldUpdateGroup() {
        // Given
        group.setName("Home Appliances");

        // When
        Group updatedGroup = groupRepository.save(group);

        // Then
        assertEquals("Home Appliances", updatedGroup.getName());
    }

    @Test
    public void shouldDeleteGroup() {
        // Given
        Group anotherGroup = new Group();
        anotherGroup.setName("Furniture");
        Product product = new Product();
        product.setName("Chair");
        product.setPrice(BigDecimal.valueOf(50));
        product.setGroup(anotherGroup);
        anotherGroup.setProducts(List.of(product));
        groupRepository.save(anotherGroup);
        Long groupId = anotherGroup.getId();
        Long productId = product.getId();

        // When
        groupRepository.delete(anotherGroup);
        Optional<Group> deletedGroup = groupRepository.findById(groupId);

        // Then

        assertTrue(deletedGroup.isEmpty(), "Group should be deleted");

        Optional<Product> foundProduct = productRepository.findById(productId);
        assertTrue(foundProduct.isPresent(), "Product should still be present");
    }

    @Test
    public void shouldNotDeleteProductWhenGroupIsDeleted() {
        // Given
        Product product = new Product();
        product.setName("Smartphone");
        product.setDescription("Latest model");
        product.setPrice(BigDecimal.valueOf(699.99));
        product.setGroup(group);

        productRepository.save(product);

        List<Product> products = new ArrayList<>();
        products.add(product);
        group.setProducts(products);

        groupRepository.save(group);

        // When
        Group foundGroup = groupRepository.findById(group.getId()).orElse(null);

        // Then
        assertNotNull(foundGroup);
        assertEquals(1, foundGroup.getProducts().size());
        assertEquals("Smartphone", foundGroup.getProducts().get(0).getName());


        Optional<Product> foundProduct = productRepository.findById(product.getId());
        assertTrue(foundProduct.isPresent());
        assertEquals("Smartphone", foundProduct.get().getName());
    }
}