package com.budget.budgetapi.domain.exception;

public class CategoryNotFoundException extends EntityNotfoundException {

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(Long categoryId) {
        this(String.format("Categoria de código %d não encontrada", categoryId));
    }

}
