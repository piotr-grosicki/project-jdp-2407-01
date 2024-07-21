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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
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

        // Then
        assertNotNull(savedGroup.getId());
        assertEquals("Toys", savedGroup.getName());
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

        // Then
        Optional<Group> deletedGroup = groupRepository.findById(groupId);
        assertTrue(deletedGroup.isEmpty());
    }

    @Test
    public void shouldHandleGroupWithProducts() {
        // Given
        Product product = new Product();
        product.setName("Smartphone");
        product.setGroup(group);

        List<Product> products = new ArrayList<>();
        products.add(product);
        group.setProducts(products);

        groupRepository.save(group);

        // When
        Group foundGroup = groupRepository.findById(group.getId()).orElse(null);
        assertNotNull(foundGroup);
        assertEquals(1, foundGroup.getProducts().size());
        assertEquals("Smartphone", foundGroup.getProducts().get(0).getName());
    }

    @Test
    public void shouldDeleteGroupWithProducts() {
        // Given
        Product product = new Product();
        product.setName("Smartphone");
        product.setGroup(group);

        group.getProducts().add(product);
        groupRepository.save(group);
        Long productId = product.getId();
        Long groupId = group.getId();

        // When
        groupRepository.delete(group);

        // Then
        assertTrue(productRepository.findById(productId).isEmpty());
        assertTrue(groupRepository.findById(groupId).isEmpty());
    }
}//