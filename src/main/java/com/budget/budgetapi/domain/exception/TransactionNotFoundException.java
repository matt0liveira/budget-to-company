package com.budget.budgetapi.domain.exception;

public class TransactionNotFoundException extends EntityNotfoundException {

    public TransactionNotFoundException(String message, Throwable cause) {
        super(message);
    }

    public TransactionNotFoundException(String code) {
        super(String.format("Transação de código %s não encontrada", code));
    }

}
