package com.ecommerce.ecommerce.Mapper.imp;

import org.springframework.stereotype.Component;

import com.ecommerce.ecommerce.Dto.In.UserInDto;
import com.ecommerce.ecommerce.Entity.User;
import com.ecommerce.ecommerce.Mapper.IMapper;

@Component
public class UserMapperImp implements IMapper<UserInDto, User>{

    @Override
    public User map(UserInDto in) {
        User user = new User();
        user.setEmail(in.getEmail());
        user.setPassword(in.getPassword());
        user.setUsername(in.getUsername());
        return user;
    }
    
}
