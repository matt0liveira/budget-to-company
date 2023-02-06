package com.budget.budgetapi.infrasctruture;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.budget.budgetapi.domain.model.ChildCategory;
import com.budget.budgetapi.domain.repository.CategoryRepositoryQueries;

public class CategoryRepositoryImpl implements CategoryRepositoryQueries {

    @Autowired
    private EntityManager manager;

    @Override
    public List<ChildCategory> findByUserId(Long userId) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(ChildCategory.class);
        var root = query.from(ChildCategory.class);

        var selection = builder.construct(ChildCategory.class, builder.literal("*"));

        query.select(selection);
        query.where(builder.equal(builder.literal(userId), root.get("user")));

        return manager.createQuery(query).getResultList();
    }

}
