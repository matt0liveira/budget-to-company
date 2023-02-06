package com.budget.budgetapi.domain.exception;

public abstract class EntityNotfoundException extends DomainException {

    public EntityNotfoundException(String message) {
        super(message);
    }

}
