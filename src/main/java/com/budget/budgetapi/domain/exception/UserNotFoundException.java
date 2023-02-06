package com.budget.budgetapi.domain.exception;

public class UserNotFoundException extends EntityNotfoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long userId) {
        this(String.format("Usuário de código %d não encontrado", userId));
    }

}
