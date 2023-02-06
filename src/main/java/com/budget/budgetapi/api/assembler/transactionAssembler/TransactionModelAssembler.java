package com.budget.budgetapi.api.assembler.transactionAssembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.budget.budgetapi.api.model.TransactionModel;
import com.budget.budgetapi.domain.model.Transaction;

@Component
public class TransactionModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public TransactionModel toModel(Transaction transaction) {
        return modelMapper.map(transaction, TransactionModel.class);
    }

    public List<TransactionModel> toCollectionModel(List<Transaction> transactions) {
        List<TransactionModel> transactionsModel = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionsModel.add(toModel(transaction));
        }

        return transactionsModel;
    }
}