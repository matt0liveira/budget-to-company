package com.budget.budgetapi.api.assembler.transactionAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.budgetapi.api.model.input.TransactionInputModel;
import com.budget.budgetapi.domain.model.Transaction;

@Component
public class TransactionInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Transaction toDomainObject(TransactionInputModel transactionInput) {
        return modelMapper.map(transactionInput, Transaction.class);
    }

    public void copyToDomainObject(TransactionInputModel transactionInput, Transaction transaction) {
        modelMapper.map(transactionInput, transaction);
    }
}
