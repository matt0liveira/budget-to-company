package com.budget.budgetapi.api.assembler.profileAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.budgetapi.api.model.input.ProfileInputModel;
import com.budget.budgetapi.domain.model.Profile;

@Component
public class ProfileInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Profile toDomainOject(ProfileInputModel profileInput) {
        return modelMapper.map(profileInput, Profile.class);
    }

    public void copyToDomainObject(ProfileInputModel profileInput, Profile profile) {
        modelMapper.map(profileInput, profile);
    }
}
