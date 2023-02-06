package com.budget.budgetapi.api.assembler.companyAssembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.budgetapi.api.model.CompanyModel;
import com.budget.budgetapi.domain.model.Company;

@Component
public class CompanyModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CompanyModel toModel(Company company) {
        return modelMapper.map(company, CompanyModel.class);
    }

    public List<CompanyModel> toCollectionModel(List<Company> companies) {
        List<CompanyModel> companiesModel = new ArrayList<>();
        
        for (Company company : companies) {
            companiesModel.add(toModel(company));
        }

        return companiesModel;
    }

}
