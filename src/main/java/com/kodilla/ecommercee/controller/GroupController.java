package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.dto.GroupDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/ecommercee/groups")
public class GroupController {

    @GetMapping
    public List<GroupDto> getGroups() {
        return new ArrayList<>();
    }

    @GetMapping("/{groupId}")
    public GroupDto getGroupById(@PathVariable Long groupId) {
        return new GroupDto(groupId, "test group name", new ArrayList<>());
    }

    @PostMapping
    public void createGroup(@RequestBody GroupDto groupDto) {
        System.out.println("Group with name " + groupDto.getName() + " created");
    }

    @PutMapping("/{groupId}")
    public GroupDto updateGroup(@PathVariable Long groupId, @RequestBody GroupDto groupDto) {
        return new GroupDto(groupId, groupDto.getName(), new ArrayList<>());
    }
}

