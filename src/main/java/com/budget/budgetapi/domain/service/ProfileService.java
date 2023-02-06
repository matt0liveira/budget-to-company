package com.budget.budgetapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.budgetapi.domain.exception.ProfileNotFoundException;
import com.budget.budgetapi.domain.model.Permission;
import com.budget.budgetapi.domain.model.Profile;
import com.budget.budgetapi.domain.repository.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PermissionService permissionService;

    @Transactional
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Transactional
    public void remove(Long profileId) {
        Profile profile = findOrfail(profileId);

        profileRepository.delete(profile);
    }

    @Transactional
    public void connectPermission(Long profileId, Long permissionId) {
        Profile profile = findOrfail(profileId);
        Permission permission = permissionService.findOrFail(permissionId);

        profile.connectPermission(permission);
    }

    @Transactional
    public void disassociatePermission(Long profileId, Long permissionId) {
        Profile profile = findOrfail(profileId);
        Permission permission = permissionService.findOrFail(permissionId);

        profile.disassociatePermission(permission);
    }

    public Profile findOrfail(Long profileId) {
        return profileRepository
                .findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(profileId));
    }
}
