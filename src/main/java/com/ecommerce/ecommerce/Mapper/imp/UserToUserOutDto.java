package com.ecommerce.ecommerce.Mapper.imp;

import org.springframework.stereotype.Component;

import com.ecommerce.ecommerce.Dto.out.UserOutDto;
import com.ecommerce.ecommerce.Entity.User;
import com.ecommerce.ecommerce.Mapper.IMapper;

@Component
public class UserToUserOutDto implements IMapper <User, UserOutDto>{

    @Override
    public UserOutDto map(User in) {
        UserOutDto userOutDto = new UserOutDto();
        userOutDto.setId(in.getId());
        userOutDto.setEmail(in.getEmail());
        userOutDto.setPassword(in.getPassword());
        userOutDto.setUsername(in.getUsername());
        return userOutDto;
    }
    
}
