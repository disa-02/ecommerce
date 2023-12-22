package com.ecommerce.ecommerce.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @OneToMany(mappedBy = "purchase",cascade = CascadeType.ALL, orphanRemoval = true)
    List<ItemPurchase> productsBuys;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    User user;

    
    public Purchase() {
    }


    public Purchase(List<ItemPurchase> productsBuys) {
        this.productsBuys = productsBuys;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public List<ItemPurchase> getProductsBuys() {
        return productsBuys;
    }


    public void setProductsBuys(List<ItemPurchase> productsBuys) {
        this.productsBuys = productsBuys;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

    
    
}
