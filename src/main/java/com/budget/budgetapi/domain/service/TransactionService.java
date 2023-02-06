// package com.budget.budgetapi.domain.service;

// import javax.transaction.Transactional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.budget.budgetapi.domain.exception.TransactionNotFoundException;
// import com.budget.budgetapi.domain.model.ChildCategory;
// import com.budget.budgetapi.domain.model.Transaction;
// import com.budget.budgetapi.domain.model.User;
// import com.budget.budgetapi.domain.repository.TransactionRepository;

// @Service
// public class TransactionService {

//     @Autowired
//     private TransactionRepository transactionRepository;

//     @Autowired
//     private CategoryService categoryService;

//     @Autowired
//     private UserService userService;

//     @Transactional
//     public Transaction save(Transaction newTransaction) {
//         return transactionRepository.save(newTransaction);
//     }

//     @Transactional
//     public void remove(Transaction transaction) {

//         transactionRepository.delete(transaction);
//     }

//     public Transaction findOrFail(String code) {
//         return transactionRepository.findByCode(code)
//                 .orElseThrow(() -> new TransactionNotFoundException(code));
//     }

//     public void validateTransaction(Transaction transaction) {
//         ChildCategory category = categoryService.findOrFail(transaction.getCategory().getId());
//         User user = userService.findOrFail(transaction.getUser().getId());

//         transaction.setCategory(category);
//         transaction.setUser(user);
//     }

// }