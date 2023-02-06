package com.budget.budgetapi.api.assembler.photoUserAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.budgetapi.api.model.PhotoUserModel;
import com.budget.budgetapi.domain.model.PhotoUser;

@Component
public class PhotoUserModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PhotoUserModel toModel(PhotoUser photo) {
        return modelMapper.map(photo, PhotoUserModel.class);
    }
}
