package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.controller.exception.GroupAlreadyExistsException;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.dto.GroupDto;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/ecommercee/groups")
@RequiredArgsConstructor
@CrossOrigin("*")
public class GroupController {

    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @GetMapping
    public ResponseEntity<List<GroupDto>> getGroups() {
        return ResponseEntity.ok(groupMapper.mapToGroupDtoList(groupService.getAllGroups()));
    }

    @SneakyThrows
    @GetMapping("{groupId}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupMapper.mapToGroupDto(groupService.getGroup(groupId)));
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestBody GroupDto groupDto) {
        if (groupService.existsGroup(groupDto.name())) {
            throw new GroupAlreadyExistsException();
        } else {
            Group group = groupMapper.mapToGroup(groupDto);
            groupService.save(group);
        }
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PutMapping(value = "/update")
    public ResponseEntity<GroupDto> updateGroup(@RequestBody GroupDto groupDto) {
        Group group = groupMapper.mapToGroup(groupDto);
        Group updatedGroup = groupService.save(group);
        return ResponseEntity.ok(groupMapper.mapToGroupDto(updatedGroup));
    }

    @SneakyThrows
    @DeleteMapping("{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) {
        Group group = groupService.getGroup(groupId);
        groupService.deleteGroup(group);
        return ResponseEntity.ok().build();
    }
}
