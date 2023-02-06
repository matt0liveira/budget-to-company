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

import com.budget.budgetapi.api.assembler.permissionAssembler.PermissionInputModelDisassembler;
import com.budget.budgetapi.api.assembler.permissionAssembler.PermissionModelAssembler;
import com.budget.budgetapi.api.model.PermissionModel;
import com.budget.budgetapi.api.model.input.PermissionInputModel;
import com.budget.budgetapi.api.openapi.controlller.PermissionControllerOpenApi;
import com.budget.budgetapi.api.utils.ResourceUriHelper;
import com.budget.budgetapi.core.security.CheckSecurity;
import com.budget.budgetapi.domain.model.Permission;
import com.budget.budgetapi.domain.repository.PermissionRepository;
import com.budget.budgetapi.domain.service.PermissionService;

@RestController
@RequestMapping("/permissions")
public class PermissionController implements PermissionControllerOpenApi {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @Autowired
    private PermissionInputModelDisassembler permissionInputModelDisassembler;

    @CheckSecurity.UsersProfilesPermissions.CanConsult
    @GetMapping
    public ResponseEntity<List<PermissionModel>> toList() {
        return ResponseEntity.ok().body(permissionModelAssembler.toCollectionModel(permissionRepository.findAll()));
    }

    @CheckSecurity.UsersProfilesPermissions.CanConsult
    @GetMapping("/{permissionId}")
    public ResponseEntity<PermissionModel> toFind(@PathVariable Long permissionId) {
        return ResponseEntity.ok().body(permissionModelAssembler.toModel(permissionService.findOrFail(permissionId)));
    }

    @CheckSecurity.UsersProfilesPermissions.CanChange
    @PostMapping
    public ResponseEntity<PermissionModel> toAdd(@RequestBody @Valid PermissionInputModel permissionInput) {
        Permission newPermission = permissionInputModelDisassembler.toDomainObject(permissionInput);

        newPermission = permissionService.save(newPermission);

        return ResponseEntity
                .created(ResourceUriHelper.addUriInResponseHeader(newPermission.getId()))
                .body(permissionModelAssembler.toModel(newPermission));
    }

    @CheckSecurity.UsersProfilesPermissions.CanChange
    @PutMapping("/{permissionId}")
    public ResponseEntity<PermissionModel> toUpdate(@PathVariable Long permissionId,
            @RequestBody @Valid PermissionInputModel permissionInput) {
        Permission permissionCurrent = permissionService.findOrFail(permissionId);

        permissionInputModelDisassembler.copyToDomainObject(permissionInput, permissionCurrent);

        permissionCurrent = permissionService.save(permissionCurrent);

        return ResponseEntity
                .ok()
                .body(permissionModelAssembler.toModel(permissionCurrent));
    }

    @CheckSecurity.UsersProfilesPermissions.CanChange
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> remove(@PathVariable Long permissionId) {
        permissionService.remove(permissionId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
