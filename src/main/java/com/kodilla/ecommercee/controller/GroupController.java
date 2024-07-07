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

    @GetMapping("/group/{groupId}")
    public GroupDto getGroupById(@PathVariable Long groupId) {
        return new GroupDto(groupId, "test group name", new ArrayList<>());
    }

    @PostMapping
    public void createGroup(@RequestParam String name) {
        System.out.println("Group with name " + name + " created");
    }

    @PutMapping(value = "{groupId}")
    public GroupDto updateGroup(@PathVariable Long groupId, @RequestParam Long productId) {
        return new GroupDto(groupId, "updated test group name", new ArrayList<>());
    }
}
