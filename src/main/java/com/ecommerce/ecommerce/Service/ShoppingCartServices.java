package com.ecommerce.ecommerce.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.Entity.ItemPurchase;
import com.ecommerce.ecommerce.Entity.Product;
import com.ecommerce.ecommerce.Entity.Purchase;
import com.ecommerce.ecommerce.Entity.ShoppingCart;
import com.ecommerce.ecommerce.Entity.User;
import com.ecommerce.ecommerce.Exception.ApiErrorException;
import com.ecommerce.ecommerce.Repository.ShoppingCartRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;

@Service
public class ShoppingCartServices {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired 
    UserRepository userRepository;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    ProductService productService;

    public List<ShoppingCart> findAll(){
        return shoppingCartRepository.findAll();
    }

    public Optional<ShoppingCart> findById(Integer id){
        return shoppingCartRepository.findById(id);
    }

    public Purchase buy(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiErrorException("User not found"));
        Purchase purchase = purchaseService.issuePurchase(user);
        user.addPurchase(purchase);
        deleteProductsFromShoppingCart(purchase.getProductsBuys(),user);
        userRepository.save(user);
        return purchase;

    }

    private void deleteProductsFromShoppingCart(List<ItemPurchase> productsBuys, User user) {
        for(ItemPurchase ip: productsBuys){
            int productId = ip.getProductId();
            Product product = productService.findById(productId).orElseThrow(() -> new ApiErrorException("Product not found"));
            user.deleteProductToSC(product, ip.getAmount());
            productService.delete(productId, ip.getAmount());;
            
        }
    }
    
}
