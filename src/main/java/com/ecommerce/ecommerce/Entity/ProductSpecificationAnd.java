package com.ecommerce.ecommerce.Entity;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProductSpecificationAnd implements ISpecification{

    private ISpecification specification1;
    private ISpecification specification2;

    public ProductSpecificationAnd(ISpecification specification1, ISpecification specification2) {
        this.specification1 = specification1;
        this.specification2 = specification2;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate1 = specification1.toPredicate(root, query, criteriaBuilder);
        Predicate predicate2 = specification2.toPredicate(root, query, criteriaBuilder);
        return criteriaBuilder.and(predicate1,predicate2);
    }
    
}
