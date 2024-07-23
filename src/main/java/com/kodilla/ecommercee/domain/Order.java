package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "DATE", nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @Column(name = "total_price", nullable = false)
    @Min(value = 0, message = "Total price cannot be negative")
    @Digits(integer = 10, fraction = 2, message = "Total price must be a valid amount")
    private BigDecimal totalPrice;

    public Order(LocalDateTime date, User user, Cart cart, BigDecimal totalPrice) {
        this.date = date;
        this.user = user;
        this.cart = cart;
        this.totalPrice = totalPrice;
    }
}