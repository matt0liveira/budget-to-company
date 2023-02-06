package com.budget.budgetapi.api.assembler.categoryAssembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.budgetapi.api.model.CategoryModelWithUser;
import com.budget.budgetapi.domain.model.ChildCategory;

@Component
public class CategoryModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CategoryModelWithUser toModel(ChildCategory category) {
        return modelMapper.map(category, CategoryModelWithUser.class);
    }

    public List<CategoryModelWithUser> toCollectionModel(List<ChildCategory> categories) {
        List<CategoryModelWithUser> categoriesModel = new ArrayList<>();
        for (ChildCategory category : categories) {
            categoriesModel.add(toModel(category));
        }

        return categoriesModel;
    }
}
