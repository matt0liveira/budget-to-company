package com.budget.budgetapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.budget.budgetapi.domain.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
