package com.budget.budgetapi.domain.exception;

public class PhotoNotFoundException extends EntityNotfoundException {

    public PhotoNotFoundException(String message) {
        super(message);
    }

    public PhotoNotFoundException(Long categoryId) {
        this(String.format("Foto do usuário de código %d não encontrada", categoryId));
    }

}