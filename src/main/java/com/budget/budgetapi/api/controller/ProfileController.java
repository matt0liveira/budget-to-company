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

import com.budget.budgetapi.api.assembler.profileAssembler.ProfileInputModelDisassembler;
import com.budget.budgetapi.api.assembler.profileAssembler.ProfileModelAssembler;
import com.budget.budgetapi.api.model.ProfileModel;
import com.budget.budgetapi.api.model.input.ProfileInputModel;
import com.budget.budgetapi.api.openapi.controlller.ProfileControllerOpenApi;
import com.budget.budgetapi.api.utils.ResourceUriHelper;
import com.budget.budgetapi.core.security.CheckSecurity;
import com.budget.budgetapi.domain.model.Profile;
import com.budget.budgetapi.domain.repository.ProfileRepository;
import com.budget.budgetapi.domain.service.ProfileService;

@RestController
@RequestMapping("/profiles")
public class ProfileController implements ProfileControllerOpenApi {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileModelAssembler profileModelAssembler;

    @Autowired
    private ProfileInputModelDisassembler profileInputModelDisassembler;

    @CheckSecurity.UsersProfilesPermissions.CanConsult
    @GetMapping
    public ResponseEntity<List<ProfileModel>> toList() {
        return ResponseEntity
                .ok()
                .body(profileModelAssembler.toCollectionModel(profileRepository.findAll()));
    }

    @CheckSecurity.UsersProfilesPermissions.CanConsult
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileModel> toFind(@PathVariable Long profileId) {
        return ResponseEntity
                .ok()
                .body(profileModelAssembler.toModel(profileService.findOrfail(profileId)));
    }

    @CheckSecurity.UsersProfilesPermissions.CanChange
    @PostMapping
    public ResponseEntity<ProfileModel> toAdd(@RequestBody @Valid ProfileInputModel profileInput) {
        Profile newprofile = profileInputModelDisassembler.toDomainOject(profileInput);

        newprofile = profileService.save(newprofile);

        return ResponseEntity
                .created(ResourceUriHelper.addUriInResponseHeader(newprofile.getId()))
                .body(profileModelAssembler.toModel(newprofile));
    }

    @CheckSecurity.UsersProfilesPermissions.CanChange
    @PutMapping("/{profileId}")
    public ResponseEntity<ProfileModel> toUpdate(@PathVariable Long profileId,
            @RequestBody @Valid ProfileInputModel profileInput) {
        Profile profileCurrent = profileService.findOrfail(profileId);

        profileInputModelDisassembler.copyToDomainObject(profileInput, profileCurrent);

        profileCurrent = profileService.save(profileCurrent);

        return ResponseEntity
                .ok()
                .body(profileModelAssembler.toModel(profileCurrent));
    }

    @CheckSecurity.UsersProfilesPermissions.CanChange
    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> remove(@PathVariable Long profileId) {
        profileService.remove(profileId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
