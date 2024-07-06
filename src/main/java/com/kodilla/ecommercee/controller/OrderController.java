package com.kodilla.ecommercee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/shop/orders")
public class OrderController {
    @GetMapping
    public ResponseEntity<List<String>> getAllOrders() {
        List<String> orders = Arrays.asList("Order1", "Order2", "Order3");
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody String order) {
        return new ResponseEntity<>("Order created: " + order, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderById(@PathVariable("id") Long id) {
        return new ResponseEntity<>("Order details for ID: " + id, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable("id") Long id, @RequestBody String order) {
        return new ResponseEntity<>("Order updated for ID: " + id + " with data: " + order, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") Long id) {
        return new ResponseEntity<>("Order deleted with ID: " + id, HttpStatus.NO_CONTENT);
    }
}
//
