package com.ecommerce.ecommerce.Entity;




import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.MapJoin;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProductSpecification implements ISpecification{

    private SearchCriteria searchCriteria;

    public ProductSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String operation = searchCriteria.getOperation();
        MapJoin<Product,String, String> attributesJoin = root.joinMap("attributes"); 

        switch (operation) {
            case "==":{
                Predicate predicateKey = criteriaBuilder.equal(attributesJoin.key(), searchCriteria.getKey());
                Predicate predicateValue = criteriaBuilder.equal(attributesJoin.value(), searchCriteria.getValue());
                return criteriaBuilder.and(predicateKey,predicateValue);
            }
            case "<":{
                Predicate predicateKey = criteriaBuilder.equal(attributesJoin.key(), searchCriteria.getKey());
                Predicate predicateValue = criteriaBuilder.lessThan(attributesJoin.value(), searchCriteria.getValue());
                return criteriaBuilder.and(predicateKey,predicateValue);

            }
            case ">":{
                Predicate predicateKey = criteriaBuilder.equal(attributesJoin.key(), searchCriteria.getKey());
                Predicate predicateValue = criteriaBuilder.greaterThan(attributesJoin.value(), searchCriteria.getValue());
                return criteriaBuilder.and(predicateKey,predicateValue);
            }
            default:
                return null;
        }
    }
}
