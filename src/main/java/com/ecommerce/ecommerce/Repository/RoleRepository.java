package com.ecommerce.ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.Entity.Role;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Integer>{
    Optional<Role> findByName(String name);
    
}
