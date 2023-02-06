package com.budget.budgetapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.budget.budgetapi.domain.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    
}
