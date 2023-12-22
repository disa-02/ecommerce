package com.ecommerce.ecommerce.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.Entity.ISpecification;
import com.ecommerce.ecommerce.Entity.Product;
import com.ecommerce.ecommerce.Entity.ProductSpecification;
import com.ecommerce.ecommerce.Entity.ProductSpecificationAnd;
import com.ecommerce.ecommerce.Entity.SearchCriteria;
import com.ecommerce.ecommerce.Exception.ApiErrorException;
import com.ecommerce.ecommerce.Repository.ProductRepository;

import jakarta.persistence.EntityManager;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    EntityManager entityManager;

    public void delete(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new ApiErrorException("Product not found");
        }
        product.get().getUser().delProduct(id);
        productRepository.deleteById(id);
    }

    public void delete(Integer id, Integer amount){
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new ApiErrorException("Product not found");
        }
        if(amount < product.get().getAmount()){
            int newAmount = product.get().getAmount() - amount;
            product.get().setAmount(newAmount);
            productRepository.save(product.get());
        }
        else{
            productRepository.deleteById(id);
        }
    }
    
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty())
            throw new ApiErrorException("Product not found");
        return product;

    }

    public List<Product> findBy(List<SearchCriteria> searchCriteriaList){
        if(searchCriteriaList.size() == 0){
            throw new ApiErrorException("The criteria list is empty");
        }
        ISpecification productSpecification = new ProductSpecification(searchCriteriaList.get(0));
        for(int i = 1; i < searchCriteriaList.size(); i++){
            ProductSpecification productSpecification2 = new ProductSpecification(searchCriteriaList.get(i));
            productSpecification = new ProductSpecificationAnd(productSpecification,productSpecification2);
        }
        return productRepository.findAll(productSpecification);
    }
    
}
