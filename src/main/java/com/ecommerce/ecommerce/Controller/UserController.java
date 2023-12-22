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

import com.ecommerce.ecommerce.Dto.In.UserInDto;
import com.ecommerce.ecommerce.Entity.User;
import com.ecommerce.ecommerce.Mapper.imp.UserToUserOutDto;
import com.ecommerce.ecommerce.Service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/api/user/")
public class UserController {
    
    @Autowired
    UserService userService;

    @Autowired
    UserToUserOutDto userToUserOutDto;



    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    } 

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #id == principal.id )")
    public ResponseEntity<?> find(@PathVariable Integer id){
        Optional<User> user = userService.findById(id);
        return new ResponseEntity<>(userToUserOutDto.map(user.get()), HttpStatus.OK);
    }
    
    @PostMapping("/create")
    @Transactional
    public ResponseEntity<?> add(@RequestBody UserInDto userInDto){
        userService.add(userInDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #id == principal.id )")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Integer id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
