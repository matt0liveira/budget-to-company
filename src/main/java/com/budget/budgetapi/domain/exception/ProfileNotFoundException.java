package com.budget.budgetapi.domain.exception;

public class ProfileNotFoundException extends EntityNotfoundException {

    public ProfileNotFoundException(String message) {
        super(message);
    }

    public ProfileNotFoundException(Long perfilId) {
        this(String.format("Perfil de código %d não encontrada", perfilId));
    }

}
