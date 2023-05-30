package com.warehouse.model;

import com.warehouse.model.enums.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private int price;
    private int quantity;
    private int weight;
    private int keep;
    private String description;
    private String img;
    private String provider;
    @ManyToOne(fetch = FetchType.LAZY)
    private Warehouse warehouse;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cart> carts;
    @OneToOne(cascade = CascadeType.ALL)
    private Stat stat;

    public Product(String name, Category category, int price, int quantity, int weight, int keep, String description, String provider) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
        this.keep = keep;
        this.description = description;
        this.provider = provider;
        this.carts = new ArrayList<>();
    }

    public void addCart(Cart cart) {
        carts.add(cart);
        cart.setProduct(this);
    }

    public void removeCart(Cart cart) {
        carts.remove(cart);
        cart.setProduct(null);
    }
}
