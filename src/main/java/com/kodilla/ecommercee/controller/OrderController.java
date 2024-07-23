package com.kodilla.ecommercee.controller;


import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.dto.OrderDto;

import com.kodilla.ecommercee.mapper.OrderMapper;
import com.kodilla.ecommercee.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/shop/orders")
@RequiredArgsConstructor
public class OrderController {

    private OrderService orderService;


    private OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders().stream()
                .map(orderMapper::mapToOrderDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        Order order = orderService.saveOrder(orderMapper.mapToOrder(orderDto));
        return ResponseEntity.ok(orderMapper.mapToOrderDto(order));
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(order));
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        Order existingOrder = orderService.getOrderById(id);

        Order orderToUpdate = orderMapper.mapToOrder(orderDto);
        orderToUpdate.setId(existingOrder.getId());

        Order updatedOrder = orderService.saveOrder(orderToUpdate);

        return ResponseEntity.ok(orderMapper.mapToOrderDto(updatedOrder));
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Order existingOrder = orderService.getOrderById(id);
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}