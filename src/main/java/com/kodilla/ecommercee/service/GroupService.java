package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.controller.exception.GroupNotFoundException;
import com.kodilla.ecommercee.domain.Group;

import com.kodilla.ecommercee.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public Group getGroup(Long id) throws GroupNotFoundException {
        return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(Group group) {
        groupRepository.delete(group);
    }

    public boolean existsGroup(String name) {
        return groupRepository.existsByName(name);
    }
}
