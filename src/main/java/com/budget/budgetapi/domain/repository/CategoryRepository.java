// package com.budget.budgetapi.domain.repository;

// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.stereotype.Repository;

// import com.budget.budgetapi.domain.model.ChildCategory;

// @Repository
// public interface CategoryRepository
//         extends JpaRepository<ChildCategory, Long>, CategoryRepositoryQueries, JpaSpecificationExecutor<ChildCategory> {

//     @Query("from Category c join fetch c.user")
//     List<ChildCategory> findAll();

//     @Query("from Category c where c.inactive = 0")
//     List<ChildCategory> findOnlyActive();
// }
