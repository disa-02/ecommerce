package com.ecommerce.ecommerce.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.Entity.ItemCart;
import com.ecommerce.ecommerce.Entity.ItemPurchase;
import com.ecommerce.ecommerce.Entity.Product;
import com.ecommerce.ecommerce.Entity.Purchase;
import com.ecommerce.ecommerce.Entity.ShoppingCart;
import com.ecommerce.ecommerce.Entity.User;
import com.ecommerce.ecommerce.Exception.ApiErrorException;
import com.ecommerce.ecommerce.Repository.PurchaseRepository;
import com.ecommerce.ecommerce.Repository.ShoppingCartRepository;

@Service
public class PurchaseService {
    
    @Autowired
    ProductService productService;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    public Purchase issuePurchase(User user){
        ShoppingCart sc = user.getShoppingCart();
        if(!sc.verifyAmountProducts()){
            throw new ApiErrorException("Purchase cannot be made, products not available");
        }

        Purchase purchase = new Purchase();
        List<ItemPurchase> products = new ArrayList<>();
        for(ItemCart ic: sc.getProducts()){
            ItemPurchase ip = new ItemPurchase();
            Product product = ic.getProduct();
            ip.setProductId(product.getId());
            ip.setProductName(product.getName());
            ip.setAmount(ic.getAmount());
            ip.setFullPrice(ic.getFullPrice());
            ip.setPurchase(purchase);
            products.add(ip);

        }
        
        purchase.setProductsBuys(products);
        purchase.setUser(user);
        return purchase;
    }

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public Purchase find(Integer id) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(() -> new ApiErrorException("Purchase not found"));
        return purchase;
    }

    public List<Purchase> findByUser(User user) {
        return purchaseRepository.findByUser(user);
    }


 
}
