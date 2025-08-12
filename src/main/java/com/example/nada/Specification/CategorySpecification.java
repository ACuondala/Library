package com.example.nada.Specification;

import com.example.nada.Dtos.CategoryDto.CategoryFilter;
import com.example.nada.Models.Category;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CategorySpecification {

    public static Specification<Category> filterBy(CategoryFilter filter){
        return (root, query,cb)->{
            List<Predicate> predicates=new ArrayList<>();
            addLikeIgnoreCase(cb,root,predicates, "name", filter.name());
            //addEqual(cb, root, predicates, "status", filter.status());

            return predicates.isEmpty()? cb.conjunction(): cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addLikeIgnoreCase(CriteriaBuilder cb, Root<Category> root, List<Predicate> predicates, String field, String value){
        if(value != null && !value.isBlank()){
            predicates.add(cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase()+ "%"));
        }
    }
    private static void addEqual(CriteriaBuilder cb, Root<Category> root,
                                 List<Predicate> predicates, String field, Object value) {
        if (value != null) {
            predicates.add(cb.equal(root.get(field), value));
        }
    }
}
