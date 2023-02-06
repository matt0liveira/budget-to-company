package com.budget.budgetapi.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.budget.budgetapi.domain.model.Transaction;
import com.budget.budgetapi.domain.model.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    Optional<Transaction> findByCode(String code);

    @Query("from Transaction t join fetch t.category join fetch t.user")
    List<Transaction> findAll();

    List<Transaction> findByUser(User user);
}
