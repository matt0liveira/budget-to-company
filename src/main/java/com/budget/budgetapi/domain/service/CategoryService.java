// package com.budget.budgetapi.domain.service;

// import javax.transaction.Transactional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.stereotype.Service;

// import com.budget.budgetapi.domain.exception.EntityInUseException;
// import com.budget.budgetapi.domain.exception.UserNotFoundException;
// import com.budget.budgetapi.domain.model.ChildCategory;
// import com.budget.budgetapi.domain.repository.CategoryRepository;

// @Service
// public class CategoryService {

//     @Autowired
//     private CategoryRepository categoryRepository;

//     @Transactional
//     public ChildCategory save(ChildCategory newCategory) {
//         return categoryRepository.save(newCategory);
//     }

//     @Transactional
//     public void remove(Long categoryId) {
//         try {
//             categoryRepository.deleteById(categoryId);
//             categoryRepository.flush();
//         } catch (DataIntegrityViolationException ex) {
//             throw new EntityInUseException("Register in use by others entities");
//         }
//     }

//     @Transactional
//     public void toActivate(Long categoryId) {
//         ChildCategory category = findOrFail(categoryId);

//         category.activate();
//     }

//     @Transactional
//     public void toInactivate(Long categoryId) {
//         ChildCategory category = findOrFail(categoryId);

//         category.inactivate();
//     }

//     public ChildCategory findOrFail(Long categoryId) {
//         return categoryRepository
//                 .findById(categoryId)
//                 .orElseThrow(() -> new UserNotFoundException(categoryId));
//     }
// }
