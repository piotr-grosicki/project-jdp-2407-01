package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public void deleteGroup(Long groupId) {
        if (groupRepository.existsById(groupId)) {
            groupRepository.deleteById(groupId);
        } else {
            throw new EntityNotFoundException("Group not found with id " + groupId);
        }
    }
}