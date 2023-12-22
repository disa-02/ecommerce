package com.ecommerce.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.Entity.Purchase;
import com.ecommerce.ecommerce.Entity.User;

import java.util.List;


public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {
    List<Purchase> findByUser(User user);
    
}
