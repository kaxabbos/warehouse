package com.warehouse.model;

import com.warehouse.model.enums.CartStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cart {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private int quantity;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    @Enumerated(EnumType.STRING)
    private CartStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public Cart(int quantity, User user) {
        this.quantity = quantity;
        this.user = user;
        this.status = CartStatus.ISSUED;
    }
}
