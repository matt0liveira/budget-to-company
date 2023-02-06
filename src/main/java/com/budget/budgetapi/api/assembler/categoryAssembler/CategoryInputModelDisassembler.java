package com.budget.budgetapi.api.assembler.categoryAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.budgetapi.api.model.input.CategoryInputModel;
import com.budget.budgetapi.domain.model.ChildCategory;

@Component
public class CategoryInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public ChildCategory toDomainObject(CategoryInputModel categoryInput) {
        return modelMapper.map(categoryInput, ChildCategory.class);
    }

    public void copyToDomainObject(CategoryInputModel categoryInput, ChildCategory category) {
        modelMapper.map(categoryInput, category);
    }
}
