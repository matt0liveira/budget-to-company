package com.budget.budgetapi.api.assembler.userAssembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.budgetapi.api.model.UserModel;
import com.budget.budgetapi.domain.model.User;

@Component
public class UserModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UserModel toModel(User user) {
        return modelMapper.map(user, UserModel.class);
    }

    public List<UserModel> toCollectionModel(List<User> users) {
        List<UserModel> usersModel = new ArrayList<>();
        for (User user : users) {
            usersModel.add(toModel(user));
        }

        return usersModel;
    }
}
