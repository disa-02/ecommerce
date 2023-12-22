package com.ecommerce.ecommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ecommerce.ecommerce.Entity.Product;
import java.util.Map;


public interface ProductRepository extends JpaRepository<Product,Integer>, JpaSpecificationExecutor<Product>{
    List<Product> findByAttributes(Map<String,String> attributes);
    
}
