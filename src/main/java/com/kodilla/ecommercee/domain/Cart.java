package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_price")
    @Min(value = 0)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal totalPrice;


    @OneToOne(mappedBy = "cart")
    private Order order;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "products_in_carts",
            joinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "cart_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "product_id")}
    )
    private List<Product> products = new ArrayList<>();
    public Cart(Long id, User user,  Order order, List<Product> products) {
        this.user = user;
        this.id = id;
        this.order = order;
        this.products = products;
    }
}
