package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
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


    @OneToOne(mappedBy = "cart")
    private Order order;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "carts")
    private List<Product> products = new ArrayList<>();

}
