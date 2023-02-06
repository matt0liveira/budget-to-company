package com.budget.budgetapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budget.budgetapi.api.assembler.permissionAssembler.PermissionModelAssembler;
import com.budget.budgetapi.api.model.PermissionModel;
import com.budget.budgetapi.api.openapi.controlller.ProfilePermissionControllerOpenApi;
import com.budget.budgetapi.core.security.CheckSecurity;
import com.budget.budgetapi.domain.exception.PermissionNotFoundException;
import com.budget.budgetapi.domain.model.Permission;
import com.budget.budgetapi.domain.model.Profile;
import com.budget.budgetapi.domain.service.PermissionService;
import com.budget.budgetapi.domain.service.ProfileService;

@RestController
@RequestMapping("/profiles/{profileId}/permissions")
public class ProfilePermissionController implements ProfilePermissionControllerOpenApi {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @CheckSecurity.UsersProfilesPermissions.CanConsult
    @GetMapping
    public ResponseEntity<List<PermissionModel>> toList(@PathVariable Long profileId) {
        Profile profile = profileService.findOrfail(profileId);

        return ResponseEntity
                .ok()
                .body(permissionModelAssembler.toCollectionModel(profile.getPermissions()));
    }

    @CheckSecurity.UsersProfilesPermissions.CanConsult
    @GetMapping("/{permissionId}")
    public ResponseEntity<PermissionModel> toFind(@PathVariable Long profileId, @PathVariable Long permissionId) {
        Profile profile = profileService.findOrfail(profileId);
        Permission permission = permissionService.findOrFail(permissionId);

        if (!profile.getPermissions().contains(permission)) {
            throw new PermissionNotFoundException(permissionId);
        }

        return ResponseEntity
                .ok()
                .body(permissionModelAssembler.toModel(permission));
    }

    @CheckSecurity.UsersProfilesPermissions.CanChange
    @PutMapping("/{permissionId}")
    public ResponseEntity<Void> toConnect(@PathVariable Long profileId, @PathVariable Long permissionId) {
        profileService.connectPermission(profileId, permissionId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @CheckSecurity.UsersProfilesPermissions.CanChange
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> toDisassociate(@PathVariable Long profileId, @PathVariable Long permissionId) {
        profileService.disassociatePermission(profileId, permissionId);

        return ResponseEntity
                .noContent()
                .build();
    }

}