package com.ecommerce.ecommerce.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    @ManyToOne
    private Role role;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Product> products;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private ShoppingCart shoppingCart;
    @OneToMany(mappedBy ="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Purchase> purchases;

    public User() {
        products = new ArrayList<>();
        this.shoppingCart = new ShoppingCart();
        this.purchases = new ArrayList<>();
    }

    public User(String username, String password, String email, Role role, List<Product> products,
            ShoppingCart shoppingCart) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.products = products;
        this.shoppingCart = shoppingCart;
        this.purchases = new ArrayList<>();
    }

    

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.products = new ArrayList<>();
        this.shoppingCart = new ShoppingCart();
        this.purchases = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public Boolean delProduct(Integer productId){
        for(int i = 0; i < products.size(); i++){
            Product product = products.get(i);
            if(product.getId() == productId){
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public void addProductToSC(Product product){
        shoppingCart.addProduct(product, 1);
    }

     public void addProductToSC(Product product, Integer amount){
        shoppingCart.addProduct(product, amount);
    }

    public void deleteProductToSC(Product product,Integer amount){
        shoppingCart.delProduct(product, amount);
    }

    public void addPurchase(Purchase purchase){
       purchases.add(purchase); 
    }


    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(role != null)
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
        return authorities; 
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    
}
