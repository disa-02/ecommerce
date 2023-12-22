package com.ecommerce.ecommerce.Mapper.imp;

import org.springframework.stereotype.Component;

import com.ecommerce.ecommerce.Dto.In.ProductInDto;
import com.ecommerce.ecommerce.Entity.Product;
import com.ecommerce.ecommerce.Mapper.IMapper;

@Component
public class ProductDtoToProduct implements IMapper<ProductInDto, Product>{


    @Override
    public Product map(ProductInDto in) {
        Product product = new Product();
        product.setName(in.getName());
        product.setAmount(in.getAmount());
        product.setAttributes(in.getAttributes());
        product.setPrice(in.getPrice());
        return product;
    }
    
}
