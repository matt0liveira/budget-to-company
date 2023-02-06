package com.budget.budgetapi.api.assembler.profileAssembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.budgetapi.api.model.ProfileModel;
import com.budget.budgetapi.domain.model.Profile;

@Component
public class ProfileModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProfileModel toModel(Profile group) {
        return modelMapper.map(group, ProfileModel.class);
    }

    public List<ProfileModel> toCollectionModel(List<Profile> groups) {
        List<ProfileModel> groupsModel = new ArrayList<>();

        for (Profile group : groups) {
            groupsModel.add(toModel(group));
        }

        return groupsModel;
    }
}
