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
public class GroupTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ProductRepository productRepository;

    private Group group;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();

        group = new Group();
        group.setName("Electronics");
        groupRepository.save(group);

        Product product = new Product();
        product.setName("Smartphone" + System.currentTimeMillis());
        product.setPrice(BigDecimal.valueOf(500));
        product.setGroup(group);
        productRepository.save(product);
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
        assertTrue(foundGroup.isPresent());
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
        groupRepository.save(anotherGroup);
        Long groupId = anotherGroup.getId();

        // When
        groupRepository.delete(anotherGroup);
        Optional<Group> deletedGroup = groupRepository.findById(groupId);

        // Then
        assertTrue(deletedGroup.isEmpty());
    }

    @Test
    public void shouldNotDeleteProductWhenGroupIsDeleted() {
        // Given
        Product product = new Product();
        product.setName("Smartphone" + System.currentTimeMillis());
        product.setPrice(BigDecimal.valueOf(500));
        product.setGroup(group);
        productRepository.save(product);
        Long productId = product.getId();

        // When
        groupRepository.delete(group);
        Optional<Product> foundProduct = productRepository.findById(productId);

        // Then
        assertTrue(foundProduct.isPresent());
    }
}