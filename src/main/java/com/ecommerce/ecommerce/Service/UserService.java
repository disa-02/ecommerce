package com.ecommerce.ecommerce.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.Dto.In.ProductInDto;
import com.ecommerce.ecommerce.Dto.In.UserInDto;
import com.ecommerce.ecommerce.Dto.out.UserOutDto;
import com.ecommerce.ecommerce.Entity.Product;
import com.ecommerce.ecommerce.Entity.Role;
import com.ecommerce.ecommerce.Entity.ShoppingCart;
import com.ecommerce.ecommerce.Entity.User;
import com.ecommerce.ecommerce.Exception.ApiErrorException;
import com.ecommerce.ecommerce.Mapper.imp.ProductDtoToProduct;
import com.ecommerce.ecommerce.Mapper.imp.ProductToProductDto;
import com.ecommerce.ecommerce.Mapper.imp.UserMapperImp;
import com.ecommerce.ecommerce.Mapper.imp.UserToUserOutDto;
import com.ecommerce.ecommerce.Repository.RoleRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapperImp userMapperImp;

    @Autowired
    ProductToProductDto productToProductDto;

    @Autowired
    ProductDtoToProduct productDtoToProduct;

    @Autowired
    UserToUserOutDto userToUserOutDto;

    @Autowired
    ShoppingCartServices shoppingCartServices;


    public List<UserOutDto> findAll() {

        List<User> users =  userRepository.findAll();
        List<UserOutDto> usersOut = new ArrayList<>();
        users.stream().forEach(user ->  usersOut.add(userToUserOutDto.map(user)));
        return usersOut;
        
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    private Role getRole(String roleName){
        Optional<Role> role = roleRepository.findByName(roleName);
        if(role.isPresent())
            return role.get();
        Role newRole = new Role(roleName);
        return roleRepository.save(newRole);
    }

    private Role getRole(){
        long cont = userRepository.count();
        if(cont == 0){
            return getRole("admin");
        }
        return getRole("user");
    }

    public void add(UserInDto userInDto) {
        User newUser = userMapperImp.map(userInDto);

        Optional<User> user = userRepository.findByUsername(newUser.getUsername());
        if(user.isPresent()){
            throw new ApiErrorException("The user is already exist");
        }
        String password = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(password);
        Role role = getRole();
        newUser.setRole(role);
        
        userRepository.save(newUser);
    }

    public void deleteUser(Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ApiErrorException("User not found");

        boolean esAdmin = false;//SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        String logName = SecurityContextHolder.getContext().getAuthentication().getName();
        int logId = userRepository.findByUsername(logName).get().getId();
        
        if(!esAdmin && logId != id){
            throw new ApiErrorException("Not authorizate");
        }
        userRepository.deleteById(id);
    }

    public ProductInDto addProduct(Integer id, ProductInDto productInDto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ApiErrorException("User not found");
        }
        Product product = productDtoToProduct.map(productInDto);
        product.setUser(user.get());
        user.get().addProduct(product);
        userRepository.save(user.get());
        return productToProductDto.map(product);
    }

    public void addProductToSc(User user, Product product,Integer amount) {
        user.addProductToSC(product,amount);
        userRepository.save(user);
    }

    public void deleteProductToSC(User user, Product product, Integer amount) {
        user.deleteProductToSC(product, amount);
        userRepository.save(user);
    }

    public Boolean delProduct(Integer id, Integer prouctId) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ApiErrorException("User not found");
        }
        Boolean delete = user.get().delProduct(prouctId);
        userRepository.save(user.get());
        return delete;
    }

    public ShoppingCart getSC(Integer id) {
        User user = findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        int scId = user.getShoppingCart().getId();
        return shoppingCartServices.findById(scId).orElseThrow(() -> new RuntimeException("SC not found")); 
    }

  

    
}
