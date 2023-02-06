package com.budget.budgetapi.api.assembler.permissionAssembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.budgetapi.api.model.PermissionModel;
import com.budget.budgetapi.domain.model.Permission;

@Component
public class PermissionModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissionModel toModel(Permission permission) {
        return modelMapper.map(permission, PermissionModel.class);
    }

    public List<PermissionModel> toCollectionModel(List<Permission> permissions) {
        List<PermissionModel> permissionsModel = new ArrayList<>();

        for (Permission permission : permissions) {
            permissionsModel.add(toModel(permission));
        }

        return permissionsModel;
    }
}
