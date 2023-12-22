package com.ecommerce.ecommerce.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = "shoppingCart")
    private List<ItemCart> products;

    public ShoppingCart() {
        products = new ArrayList<>();
    }

    public ShoppingCart(List<ItemCart> products) {
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ItemCart> getProducts() {
        return products;
    }

    public void setProducts(List<ItemCart> products) {
        this.products = products;
    }

    public void addProduct(Product product, Integer amount){
        for(ItemCart item: products){
            if(item.getProduct() == product){
                item.setAmount(item.getAmount() + amount);
                return ;
            }
        }
        ItemCart newItem = new ItemCart(product, amount);
        newItem.setShoppingCart(this);
        products.add(newItem);
    }

    public void delProduct(Product product, Integer amount){
        for(int i = 0; i < products.size(); i++){
            ItemCart item = products.get(i);
            if(item.getProduct().equals(product)){
                Integer itemAmount = item.getAmount();
                if(itemAmount > amount)
                    item.setAmount(itemAmount - amount);
                else
                    products.remove(i);
                return ;
            }   
        }
    }

    public Double getFullPrice(){
        Double price = 0.0;
        for(ItemCart ic: products){
            price += ic.getFullPrice();
        }
        return price;
    }
    
    public Boolean verifyAmountProducts(){
        for(ItemCart ic: products){
            if(!ic.verifyAmount())
                return false;
        }
        return true;
    }

    
    
}
