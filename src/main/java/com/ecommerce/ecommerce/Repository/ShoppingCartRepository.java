package com.ecommerce.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.Entity.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {
    
}
