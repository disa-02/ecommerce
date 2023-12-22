package com.ecommerce.ecommerce.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer amount;
    private Double price;
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    private Map<String,String> attributes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<ItemCart> items;

    
    public Product() {
        attributes = new HashMap<>();
    }





    public List<ItemCart> getItems() {
        return items;
    }



    public void setItems(List<ItemCart> items) {
        this.items = items;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String name, String value){
        attributes.put(name, value);
    }

    public void delAttribute(String name){
        attributes.remove(name);
    }
    
    

    @Override
    public boolean equals(Object obj) {
        return this.id == ((Product) obj).getId();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }





    public Double getPrice() {
        return price;
    }





    public void setPrice(Double price) {
        this.price = price;
    }
    

    
}
