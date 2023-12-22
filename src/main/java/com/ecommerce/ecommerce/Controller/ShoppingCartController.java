package com.ecommerce.ecommerce.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.Dto.In.ScartProduct;
import com.ecommerce.ecommerce.Entity.Product;
import com.ecommerce.ecommerce.Entity.Purchase;
import com.ecommerce.ecommerce.Entity.ShoppingCart;
import com.ecommerce.ecommerce.Entity.User;
import com.ecommerce.ecommerce.Service.ProductService;
import com.ecommerce.ecommerce.Service.ShoppingCartServices;
import com.ecommerce.ecommerce.Service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/shoppingCart")
public class ShoppingCartController {
    
    @Autowired
    ShoppingCartServices shoppingCartServices;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(shoppingCartServices.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> find(@PathVariable Integer id){
        Optional<ShoppingCart> shoppingCart = shoppingCartServices.findById(id);
        if(shoppingCart.isEmpty()){
            return new ResponseEntity<>("ShoppingCart not found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(shoppingCart.get(), HttpStatus.OK);
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == principal.id )")
    public ResponseEntity<?> getUserShoppingCart(@PathVariable Integer userId){
        ShoppingCart sc = userService.getSC(userId);
        return new ResponseEntity<>(sc, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{userId}/addProductToSC")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == principal.id )")
    @Transactional
    public ResponseEntity<?> addProductToSC(@PathVariable Integer userId, @RequestBody ScartProduct scartProduct){
        Optional<User> user = userService.findById(userId);
        if(user.isEmpty()){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        Optional<Product> product = productService.findById(scartProduct.getId());
        if(product.isEmpty()){
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        if(scartProduct.getAmount() > product.get().getAmount())
            throw new RuntimeException("Amount not disponible");
        userService.addProductToSc(user.get(),product.get(),scartProduct.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("buy/{userId}")
    @PreAuthorize("hasRole('USER') and #userId == principal.id")
    @Transactional
    public ResponseEntity<?> buy(@PathVariable Integer userId){
        Purchase purchase = shoppingCartServices.buy(userId);
        return new ResponseEntity<>(purchase,HttpStatus.OK);
    }
    
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{userId}/deleteProductToSC")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == principal.id )")
    @Transactional
    public ResponseEntity<?> deleteProductToSC(@PathVariable Integer userId, @RequestBody ScartProduct scartProduct){
        Optional<User> user = userService.findById(userId);
        if(user.isEmpty()){
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        Optional<Product> product = productService.findById(scartProduct.getId());
        if(product.isEmpty()){
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        userService.deleteProductToSC(user.get(),product.get(),scartProduct.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
