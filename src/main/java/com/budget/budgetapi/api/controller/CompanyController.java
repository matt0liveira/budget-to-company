package com.budget.budgetapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budget.budgetapi.api.assembler.companyAssembler.CompanyModelAssembler;
import com.budget.budgetapi.api.model.CompanyModel;
import com.budget.budgetapi.domain.repository.CompanyRepository;
import com.budget.budgetapi.domain.service.CompanyService;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyModelAssembler companyModelAssembler;

    @GetMapping
    public ResponseEntity<List<CompanyModel>> findAll() {
        return ResponseEntity.ok(companyModelAssembler.toCollectionModel(companyRepository.findAll()));
    }
    
}
