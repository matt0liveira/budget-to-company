package com.budget.budgetapi.api.assembler.userAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.budgetapi.api.model.input.UserInputModel;
import com.budget.budgetapi.domain.model.User;

@Component
public class UserInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public User toDomainObject(UserInputModel userInput) {
        return modelMapper.map(userInput, User.class);
    }

    public void copyToDomainObject(UserInputModel userInput, User user) {
        modelMapper.map(userInput, user);
    }
}
