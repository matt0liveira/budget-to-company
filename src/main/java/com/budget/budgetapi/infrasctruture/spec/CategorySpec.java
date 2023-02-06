package com.budget.budgetapi.infrasctruture.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.budget.budgetapi.domain.filter.CategoryFilter;
import com.budget.budgetapi.domain.model.ChildCategory;

public class CategorySpec {

    public static Specification<ChildCategory> usingFilter(CategoryFilter filter) {
        return (root, query, builder) -> {
            if (ChildCategory.class.equals(query.getResultType())) {
                root.fetch("user");
            }

            var predicates = new ArrayList<Predicate>();

            if (filter.getUserId() != null) {
                predicates.add(builder.equal(root.get("user"), filter.getUserId()));
            }

            if (filter.getOnlyActive() != null && filter.getOnlyActive()) {
                predicates.add(builder.equal(root.get("inactive"), Boolean.FALSE));
            }

            if(filter.getDescription() != null) {
                predicates.add(builder.like(root.get("description"), "%" + filter.getDescription() + "%"));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
