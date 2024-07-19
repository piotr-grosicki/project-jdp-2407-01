package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(name = "total_price", nullable = false)
    @Min(value = 0, message = "Total price cannot be negative")
    @Digits(integer = 10, fraction = 2, message = "Total price must be a valid amount")
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Order order;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "products_in_carts",
            joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    public Cart(User user, Order order, List<Product> products) {
        this.user = user;
        this.order = order;
        this.products = products;
    }
}
