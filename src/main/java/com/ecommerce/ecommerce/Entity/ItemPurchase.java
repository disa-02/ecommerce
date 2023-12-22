package com.ecommerce.ecommerce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productId;
    private String productName;
    private Integer amount;
    private Double fullPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Purchase purchase;

    
    public ItemPurchase() {
    }


    public ItemPurchase(String productName, Integer amount, Double fullPrice) {
        this.productName = productName;
        this.amount = amount;
        this.fullPrice = fullPrice;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getProductName() {
        return productName;
    }


    public Integer getProductId() {
        return productId;
    }


    public void setProductId(Integer productId) {
        this.productId = productId;
    }


    public Purchase getPurchase() {
        return purchase;
    }


    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }


    public Integer getAmount() {
        return amount;
    }


    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    public Double getFullPrice() {
        return fullPrice;
    }


    public void setFullPrice(Double fullPrice) {
        this.fullPrice = fullPrice;
    }

    
    


}
