package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.controller.exception.ProductNotFoundException;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.dto.GroupDto;
import com.kodilla.ecommercee.service.GroupService;
import com.kodilla.ecommercee.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupMapper {

    private final ProductService productService;

    public Group mapToGroup(GroupDto groupDto) {
        List<Product> productsToMap = new ArrayList<>();
        return new Group(
                groupDto.id(),
                groupDto.name(),
                groupDto.products().stream()
                        .map(id -> {
                            try {
                                return mapLongToProduct(id);
                            } catch (ProductNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }).toList()
        );
    }

    public GroupDto mapToGroupDto(Group group) {
        return new GroupDto(
                group.getId(),
                group.getName(),
                group.getProducts().stream()
                        .map(Product::getId)
                        .toList()
        );
    }

    public List<GroupDto> mapToGroupDtoList(List<Group> groups) {
        return groups.stream()
                .map(this::mapToGroupDto)
                .toList();
    }

    private Product mapLongToProduct(Long id) throws ProductNotFoundException {
        return productService.getProduct(id);
    }
}
