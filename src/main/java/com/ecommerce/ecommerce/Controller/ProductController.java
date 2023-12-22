package com.ecommerce.ecommerce.Controller;

import java.util.List;
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

import com.ecommerce.ecommerce.Dto.In.ProductInDto;
import com.ecommerce.ecommerce.Entity.Product;
import com.ecommerce.ecommerce.Entity.SearchCriteria;
import com.ecommerce.ecommerce.Service.ProductService;
import com.ecommerce.ecommerce.Service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;
    
    @GetMapping("")
    public ResponseEntity<?> findAll(){
        List<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        Optional<Product> product = productService.findById(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/findBy")
    public ResponseEntity<?> findBy(@RequestBody List<SearchCriteria> searchCriteria){
        List<Product> products = productService.findBy(searchCriteria);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{userId}/")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == principal.id )")
    @Transactional
    public ResponseEntity<?> addProduct(@PathVariable Integer userId, @RequestBody ProductInDto productInDto){
        userService.addProduct(userId,productInDto);
        return new ResponseEntity<>(productInDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Integer id){
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{prouctId}/{userId}/")
    @Transactional
    public ResponseEntity<?> delProduct(@PathVariable Integer userId, @PathVariable Integer prouctId){
        Boolean delete = userService.delProduct(userId,prouctId);
        if(delete)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }

}
