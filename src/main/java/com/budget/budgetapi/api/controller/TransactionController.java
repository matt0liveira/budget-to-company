// package com.budget.budgetapi.api.controller;

// import java.lang.reflect.Field;
// import java.math.BigDecimal;
// import java.util.List;
// import java.util.Map;

// import javax.servlet.http.HttpServletRequest;
// import javax.validation.Valid;

// import org.flywaydb.core.internal.util.ExceptionUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.http.ResponseEntity;
// import org.springframework.http.converter.HttpMessageNotReadableException;
// import org.springframework.http.server.ServletServerHttpRequest;
// import org.springframework.security.access.AccessDeniedException;
// import org.springframework.util.ReflectionUtils;
// import org.springframework.validation.BeanPropertyBindingResult;
// import org.springframework.validation.SmartValidator;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PatchMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.fasterxml.jackson.databind.DeserializationFeature;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.budget.budgetapi.api.assembler.transactionAssembler.TransactionInputModelDisassembler;
// import com.budget.budgetapi.api.assembler.transactionAssembler.TransactionModelAssembler;
// import com.budget.budgetapi.api.model.TransactionModel;
// import com.budget.budgetapi.api.model.input.TransactionInputModel;
// import com.budget.budgetapi.api.openapi.controlller.TransactionControllerOpenApi;
// import com.budget.budgetapi.api.utils.ResourceUriHelper;
// import com.budget.budgetapi.core.security.CheckSecurity;
// import com.budget.budgetapi.core.security.SecurityUtils;
// import com.budget.budgetapi.core.validation.ValidationException;
// import com.budget.budgetapi.domain.exception.InvalidValueException;
// import com.budget.budgetapi.domain.filter.TransactionFilter;
// import com.budget.budgetapi.domain.model.Transaction;
// import com.budget.budgetapi.domain.model.User;
// import com.budget.budgetapi.domain.model.enums.TypeTransaction;
// import com.budget.budgetapi.domain.repository.TransactionRepository;
// import com.budget.budgetapi.domain.service.TransactionService;
// import com.budget.budgetapi.infrasctruture.spec.TransactionSpec;

// @RestController
// @RequestMapping("/transactions")
// public class TransactionController implements TransactionControllerOpenApi {

//     @Autowired
//     private TransactionRepository transactionRepository;

//     @Autowired
//     private TransactionModelAssembler transactionModelAssembler;

//     @Autowired
//     private TransactionInputModelDisassembler transactionInputModelDisassembler;

//     @Autowired
//     private TransactionService transactionService;

//     @Autowired
//     private SmartValidator validator;

//     @Autowired
//     private SecurityUtils securityUtils;

//     @CheckSecurity.Transactions.CanSearch
//     @GetMapping
//     public ResponseEntity<List<TransactionModel>> toSearch(TransactionFilter filter) {
//         return ResponseEntity.ok()
//                 .body(transactionModelAssembler
//                         .toCollectionModel(transactionRepository.findAll(TransactionSpec.usingFilter(filter))));
//     }

//     @CheckSecurity.Transactions.CanFind
//     @GetMapping("/{transactionCode}")
//     public TransactionModel toFind(@PathVariable String transactionCode) {
//         return transactionModelAssembler.toModel(transactionService.findOrFail(transactionCode));
//     }

//     @CheckSecurity.Transactions.CanAdd
//     @PostMapping
//     public ResponseEntity<TransactionModel> toAdd(@RequestBody @Valid TransactionInputModel transactionInput) {
//         try {
//             Transaction newTransaction = transactionInputModelDisassembler.toDomainObject(transactionInput);

//             newTransaction.setUser(new User());
//             newTransaction.getUser().setId(securityUtils.getUserIdAuthenticated());

//             transactionService.validateTransaction(newTransaction);
//             verifyTypeTransaction(newTransaction);
//             newTransaction = transactionService.save(newTransaction);

//             return ResponseEntity.created(ResourceUriHelper.addUriInResponseHeader(newTransaction.getCode()))
//                     .body(transactionModelAssembler.toModel(newTransaction));
//         } catch (DataIntegrityViolationException ex) {
//             throw new InvalidValueException(
//                     "Valor inválido. Verifique os valores disponíveis e informe um valor válido.");
//         }
//     }

//     @CheckSecurity.Transactions.CanUpdate
//     @PatchMapping("/{transactionCode}")
//     public TransactionModel toPartialUpdate(@PathVariable String transactionCode,
//             @RequestBody Map<String, Object> fields, HttpServletRequest request) {
//         try {
//             Transaction transactionCurrent = transactionService.findOrFail(transactionCode);

//             this.merge(fields, transactionCurrent, request);

//             validate(transactionCurrent, "transaction");

//             if (fields.containsKey("value")) {
//                 verifyTypeTransaction(transactionCurrent);
//             }

//             transactionService.validateTransaction(transactionCurrent);
//             transactionCurrent = transactionService.save(transactionCurrent);

//             return transactionModelAssembler.toModel(transactionCurrent);
//         } catch (DataIntegrityViolationException ex) {
//             throw new InvalidValueException(
//                     "Valor inválido. Verifique os valores disponíveis e informe um valor válido.");
//         }
//     }

//     @DeleteMapping("/{transactionCode}")
//     public ResponseEntity<Void> remove(@PathVariable String transactionCode) {
//         Transaction transaction = transactionService.findOrFail(transactionCode);

//         if (!securityUtils.canUpdateTransactions(transaction.getUser().getId())) {
//             throw new AccessDeniedException("Você não tem permissão para realizar determinada ação");
//         }

//         validateTypeTransaction(transaction);
//         transactionService.remove(transaction);

//         return ResponseEntity.noContent().build();
//     }

//     private void validateTypeTransaction(Transaction transaction) {
//         if (transaction.getType().equals(TypeTransaction.EXPENSE)) {
//             transaction.getUser().setBalance(transaction.getUser().getBalance().add(transaction.getValue()));
//         } else {
//             transaction.getUser().setBalance(transaction.getUser().getBalance().subtract(transaction.getValue()));
//         }
//     }

//     private void verifyTypeTransaction(Transaction transaction) {
//         BigDecimal balanceUser = transaction.getUser().getBalance();

//         if (transaction.getType().equals(TypeTransaction.EXPENSE)) {
//             transaction.getUser().setBalance(
//                     balanceUser.subtract(transaction.getValue()));
//         } else {
//             transaction.getUser()
//                     .setBalance(balanceUser.add(transaction.getValue()));
//         }
//     }

//     private void validate(Transaction transactionCurrent, String objName) {
//         BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(transactionCurrent, objName);

//         validator.validate(transactionCurrent, bindingResult);

//         if (bindingResult.hasErrors()) {
//             throw new ValidationException(bindingResult);
//         }
//     }

//     private void merge(Map<String, Object> fields, Transaction transactionCurrent, HttpServletRequest request) {
//         ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

//         try {
//             ObjectMapper objectMapper = new ObjectMapper();
//             objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//             Transaction transaction = objectMapper.convertValue(fields, Transaction.class);

//             fields.forEach((name, value) -> {
//                 Field field = ReflectionUtils.findField(Transaction.class, name);
//                 if (field != null) {
//                     field.setAccessible(true);
//                 }

//                 Object newValue = ReflectionUtils.getField(field, transaction);

//                 ReflectionUtils.setField(field, transactionCurrent, newValue);
//             });
//         } catch (IllegalArgumentException e) {
//             Throwable cause = ExceptionUtils.getRootCause(e);

//             throw new HttpMessageNotReadableException(e.getMessage(), cause, serverHttpRequest);
//         }

//     }
// }
