package com.budget.budgetapi.infrasctruture.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.budget.budgetapi.domain.filter.TransactionFilter;
import com.budget.budgetapi.domain.model.Transaction;

public class TransactionSpec {

    public static Specification<Transaction> usingFilter(TransactionFilter filter) {
        return (root, query, builder) -> {
            if (Transaction.class.equals(query.getResultType())) {
                root.fetch("category");
                root.fetch("user");
            }

            var predicates = new ArrayList<Predicate>();

            if (filter.getType() != null) {
                predicates.add(builder.equal(root.get("type"), filter.getType()));
            }

            if (filter.getUserId() != null) {
                predicates.add(builder.equal(root.get("user"), filter.getUserId()));
            }

            if (filter.getCategoryId() != null) {
                predicates.add(builder.equal(root.get("category"), filter.getCategoryId()));
            }

            if (filter.getDateTransactionInitial() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("date"), filter.getDateTransactionInitial()));
            }

            if (filter.getDateTransactionFinal() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("date"), filter.getDateTransactionFinal()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}