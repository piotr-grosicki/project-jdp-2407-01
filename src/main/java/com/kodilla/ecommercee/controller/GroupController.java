package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.dto.GroupDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/ecommercee/groups")
public class GroupController {

    @GetMapping
    public ResponseEntity<List<GroupDto>> getGroups() {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long groupId) {
        return new ResponseEntity<>(new GroupDto(groupId, "test group name", new ArrayList<>()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestParam String name) {
        System.out.println("Group with name " + name + " created");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable Long groupId, @RequestParam Long productId) {
        return new ResponseEntity<>(new GroupDto(groupId, "updated test group name", new ArrayList<>()), HttpStatus.OK);
    }
}
