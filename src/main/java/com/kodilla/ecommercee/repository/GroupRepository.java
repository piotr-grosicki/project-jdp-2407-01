package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    boolean existsByName(String name);
}