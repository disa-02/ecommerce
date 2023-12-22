package com.ecommerce.ecommerce.Mapper.imp;

import org.springframework.stereotype.Component;

import com.ecommerce.ecommerce.Dto.In.ProductInDto;
import com.ecommerce.ecommerce.Entity.Product;
import com.ecommerce.ecommerce.Mapper.IMapper;

@Component
public class ProductToProductDto implements IMapper <Product, ProductInDto>{

    @Override
    public ProductInDto map(Product in) {
        ProductInDto productInDto = new ProductInDto();
        productInDto.setName(in.getName());
        productInDto.setAmount(in.getAmount());
        productInDto.setAttributes(in.getAttributes());
        return productInDto;
    }
    
}
