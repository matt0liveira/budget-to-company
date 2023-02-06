package com.budget.budgetapi.domain.repository;

import com.budget.budgetapi.domain.model.PhotoUser;

public interface UserRepositoryQueries {

    PhotoUser save(PhotoUser photo);

    void delete(PhotoUser photo);
}
