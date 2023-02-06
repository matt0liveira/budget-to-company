package com.budget.budgetapi.domain.repository;

import java.util.List;

import com.budget.budgetapi.domain.model.ChildCategory;

public interface CategoryRepositoryQueries {

    List<ChildCategory> findByUserId(Long userId);

}
