package com.budget.budgetapi.domain.exception;

public class EntityInUseException extends RuntimeException {

    public EntityInUseException(String arg0) {
        super(arg0);
    }

    public EntityInUseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
