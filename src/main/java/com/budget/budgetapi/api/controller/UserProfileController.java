package com.budget.budgetapi.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budget.budgetapi.api.assembler.profileAssembler.ProfileModelAssembler;
import com.budget.budgetapi.api.model.ProfileModel;
import com.budget.budgetapi.api.openapi.controlller.UserProfileControllerOpenApi;
import com.budget.budgetapi.core.security.CheckSecurity;
import com.budget.budgetapi.domain.exception.ProfileNotFoundException;
import com.budget.budgetapi.domain.model.Profile;
import com.budget.budgetapi.domain.model.User;
import com.budget.budgetapi.domain.service.ProfileService;
import com.budget.budgetapi.domain.service.UserService;

@RestController
@RequestMapping("/users/{userId}/profiles")
public class UserProfileController implements UserProfileControllerOpenApi {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileModelAssembler profileModelAssembler;

    @CheckSecurity.UsersProfilesPermissions.CanConsult
    @GetMapping
    public ResponseEntity<List<ProfileModel>> toList(@PathVariable Long userId) {
        User user = userService.findOrFail(userId);

        List<Profile> profiles = new ArrayList<>(user.getProfiles());

        return ResponseEntity.ok().body(profileModelAssembler.toCollectionModel(profiles));
    }

    @CheckSecurity.UsersProfilesPermissions.CanConsult
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileModel> toFind(@PathVariable Long userId, @PathVariable Long profileId) {
        User user = userService.findOrFail(userId);
        Profile profile = profileService.findOrfail(profileId);

        if (!user.getProfiles().contains(profile)) {
            throw new ProfileNotFoundException(profileId);
        }

        return ResponseEntity.ok().body(profileModelAssembler.toModel(profile));
    }

    @CheckSecurity.UsersProfilesPermissions.CanChange
    @PutMapping("/{profileId}")
    public ResponseEntity<Void> connectProfile(@PathVariable Long userId, @PathVariable Long profileId) {
        userService.connectProfile(userId, profileId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsersProfilesPermissions.CanChange
    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> disassociateProfile(@PathVariable Long userId, @PathVariable Long profileId) {
        userService.disassociateProfile(userId, profileId);

        return ResponseEntity.noContent().build();
    }
}
