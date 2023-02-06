package com.budget.budgetapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.budgetapi.domain.model.Company;
import com.budget.budgetapi.domain.repository.CompanyRepository;

@Service
public class CompanyService {
    
    @Autowired
    private CompanyRepository companyRepository;

    public Company save(Company newCompany) {
        newCompany = companyRepository.save(newCompany);

        return newCompany;
    }

}
