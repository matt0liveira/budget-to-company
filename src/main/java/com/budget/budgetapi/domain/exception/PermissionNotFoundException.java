package com.budget.budgetapi.domain.exception;

public class PermissionNotFoundException extends EntityNotfoundException {

    public PermissionNotFoundException(String message) {
        super(message);
    }

    public PermissionNotFoundException(Long categoryId) {
        this(String.format("Permissão de código %d não encontrada", categoryId));
    }

}
