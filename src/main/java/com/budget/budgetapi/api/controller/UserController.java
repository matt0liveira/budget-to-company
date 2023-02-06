package com.budget.budgetapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budget.budgetapi.api.assembler.userAssembler.UserInputModelDisassembler;
import com.budget.budgetapi.api.assembler.userAssembler.UserModelAssembler;
import com.budget.budgetapi.api.model.UserModel;
import com.budget.budgetapi.api.model.input.ChangePasswordInputModel;
import com.budget.budgetapi.api.model.input.UserInputModel;
import com.budget.budgetapi.api.model.input.UserWithPasswordInputModel;
import com.budget.budgetapi.api.openapi.controlller.UserControllerOpenApi;
import com.budget.budgetapi.api.utils.ResourceUriHelper;
import com.budget.budgetapi.core.security.CheckSecurity;
import com.budget.budgetapi.domain.model.User;
import com.budget.budgetapi.domain.repository.UserRepository;
import com.budget.budgetapi.domain.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController implements UserControllerOpenApi {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserModelAssembler userAssembler;

    @Autowired
    private UserInputModelDisassembler userInputModelDisassembler;

    @CheckSecurity.UsersProfilesPermissions.CanConsult
    @GetMapping
    public ResponseEntity<List<UserModel>> toList() {
        List<UserModel> users = userAssembler.toCollectionModel(userRepository.findAll());

        return ResponseEntity.ok().body(users);
    }

    @CheckSecurity.UsersProfilesPermissions.CanConsultUser
    @GetMapping("/{userId}")
    public ResponseEntity<UserModel> toFind(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userAssembler.toModel(userService.findOrFail(userId)));
    }

    @PostMapping
    public ResponseEntity<UserModel> toAdd(@RequestBody @Valid UserWithPasswordInputModel userInput) {
        User newUser = userInputModelDisassembler.toDomainObject(userInput);

        newUser = userService.save(newUser);

        return ResponseEntity
                .created(ResourceUriHelper.addUriInResponseHeader(newUser.getId()))
                .body(userAssembler.toModel(newUser));
    }

    @CheckSecurity.UsersProfilesPermissions.CanChangeUser
    @PutMapping("/{userId}")
    public ResponseEntity<UserModel> toUpdate(@PathVariable Long userId, @RequestBody @Valid UserInputModel userInput) {
        User userCurrent = userService.findOrFail(userId);

        userInputModelDisassembler.copyToDomainObject(userInput, userCurrent);

        userCurrent = userService.save(userCurrent);

        return ResponseEntity.ok().body(userAssembler.toModel(userCurrent));
    }

    @CheckSecurity.UsersProfilesPermissions.CanChangeUser
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> remove(@PathVariable Long userId) {
        userService.remove(userId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsersProfilesPermissions.CanChangePassword
    @PutMapping("/change-password/{userId}")
    public ResponseEntity<Void> toChangePassword(@PathVariable Long userId,
            @RequestBody @Valid ChangePasswordInputModel changePasswordInput) {

        userService.toChangePassword(userId, changePasswordInput.getPasswordCurrent(),
                changePasswordInput.getNewPassword());

        return ResponseEntity.noContent().build();
    }
}