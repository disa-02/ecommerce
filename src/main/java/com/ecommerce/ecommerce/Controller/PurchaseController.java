package com.ecommerce.ecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.Entity.Purchase;
import com.ecommerce.ecommerce.Entity.User;
import com.ecommerce.ecommerce.Exception.ApiErrorException;
import com.ecommerce.ecommerce.Service.PurchaseService;
import com.ecommerce.ecommerce.Service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    
    @Autowired
    PurchaseService purchaseService;

    @Autowired 
    UserService userService;
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> findAll() {
        List<Purchase> purchases = purchaseService.findAll(); 
        return new ResponseEntity<>(purchases,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Purchase purchase = purchaseService.find(id);
        return new ResponseEntity<>(purchase,HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == principal.id )")
    @GetMapping("/{id}/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> find(@PathVariable Integer id, @PathVariable Integer userId) {
        Purchase purchase = purchaseService.find(id);
        return new ResponseEntity<>(purchase,HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == principal.id )")
    @GetMapping("/findByUser/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> findByUser(@PathVariable Integer userId) {
        User user = userService.findById(userId).orElseThrow(() -> new ApiErrorException("User not found"));
        List<Purchase> purchases = purchaseService.findByUser(user);
        return new ResponseEntity<>(purchases,HttpStatus.OK);
    }
}
