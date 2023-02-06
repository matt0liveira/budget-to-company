package com.budget.budgetapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.budgetapi.domain.exception.PermissionNotFoundException;
import com.budget.budgetapi.domain.model.Permission;
import com.budget.budgetapi.domain.repository.PermissionRepository;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Transactional
    public void remove(Long permissionId) {
        Permission permission = findOrFail(permissionId);

        permissionRepository.delete(permission);
    }

    public Permission findOrFail(Long permissionId) {
        return permissionRepository
                .findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException(permissionId));
    }
}
