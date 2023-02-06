// package com.budget.budgetapi.api.controller;

// import java.util.List;

// import javax.validation.Valid;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.AccessDeniedException;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.web.bind.annotation.RestController;

// import com.budget.budgetapi.api.assembler.categoryAssembler.CategoryInputModelDisassembler;
// import com.budget.budgetapi.api.assembler.categoryAssembler.CategoryModelAssembler;
// import com.budget.budgetapi.api.model.CategoryModelWithUser;
// import com.budget.budgetapi.api.model.input.CategoryInputModel;
// import com.budget.budgetapi.api.openapi.controlller.CategoryControllerOpenApi;
// import com.budget.budgetapi.api.utils.ResourceUriHelper;
// import com.budget.budgetapi.core.security.CheckSecurity;
// import com.budget.budgetapi.core.security.SecurityUtils;
// import com.budget.budgetapi.domain.filter.CategoryFilter;
// import com.budget.budgetapi.domain.model.ChildCategory;
// import com.budget.budgetapi.domain.model.User;
// import com.budget.budgetapi.domain.repository.CategoryRepository;
// import com.budget.budgetapi.domain.service.CategoryService;
// import com.budget.budgetapi.infrasctruture.spec.CategorySpec;

// @RestController
// @RequestMapping("/categories")
// public class CategoryController implements CategoryControllerOpenApi {

//     @Autowired
//     private CategoryRepository categoryRepository;

//     @Autowired
//     private CategoryService categoryService;

//     @Autowired
//     private CategoryModelAssembler categoryModelAssembler;

//     @Autowired
//     private CategoryInputModelDisassembler categoryInputDisassembler;

//     @Autowired
//     private SecurityUtils securityUtils;

//     @CheckSecurity.Categories.CanConsult
//     @GetMapping
//     public ResponseEntity<List<CategoryModelWithUser>> toList(CategoryFilter filter) {
//         return ResponseEntity.ok().body(
//                 categoryModelAssembler.toCollectionModel(categoryRepository.findAll(CategorySpec.usingFilter(filter))));
//     }

//     @CheckSecurity.Categories.CanFind
//     @GetMapping("/{categoryId}")
//     public CategoryModelWithUser toFind(@PathVariable Long categoryId) {
//         return categoryModelAssembler.toModel(categoryService.findOrFail(categoryId));
//     }

//     @CheckSecurity.Categories.CanAdd
//     @PostMapping
//     public ResponseEntity<CategoryModelWithUser> toAdd(@RequestBody @Valid CategoryInputModel categoryInput) {
//         ChildCategory newCategory = categoryInputDisassembler.toDomainObject(categoryInput);

//         newCategory.setUser(new User());
//         newCategory.getUser().setId(securityUtils.getUserIdAuthenticated());

//         newCategory = categoryService.save(newCategory);

//         return ResponseEntity
//                 .created(ResourceUriHelper.addUriInResponseHeader(newCategory.getId()))
//                 .body(categoryModelAssembler.toModel(newCategory));
//     }

//     @CheckSecurity.Categories.CanUpdate
//     @PutMapping("/{categoryId}")
//     @ResponseStatus(code = HttpStatus.OK)
//     public CategoryModelWithUser toUpdate(@PathVariable Long categoryId,
//             @RequestBody @Valid CategoryInputModel categoryInput) {
//         ChildCategory categoryCurrent = categoryService.findOrFail(categoryId);

//         categoryInputDisassembler.copyToDomainObject(categoryInput, categoryCurrent);

//         categoryCurrent = categoryService.save(categoryCurrent);

//         return categoryModelAssembler.toModel(categoryCurrent);
//     }

//     @DeleteMapping("/{categoryId}")
//     public ResponseEntity<Void> remove(@PathVariable Long categoryId) {
//         ChildCategory category = categoryService.findOrFail(categoryId);

//         if (!securityUtils.canChangeCategories(category.getUser().getId())) {
//             throw new AccessDeniedException("Você não tem permissão para realizar determinada ação.");
//         }

//         categoryService.remove(categoryId);

//         return ResponseEntity.noContent().build();
//     }

//     @DeleteMapping("/{categoryId}/inactive")
//     public ResponseEntity<Void> toActivate(@PathVariable Long categoryId) {
//         ChildCategory category = categoryService.findOrFail(categoryId);

//         if (!securityUtils.canChangeCategories(category.getUser().getId())) {
//             throw new AccessDeniedException("Você não tem permissão para realizar determinada ação.");
//         }

//         categoryService.toActivate(categoryId);

//         return ResponseEntity.noContent().build();
//     }

//     @PutMapping("/{categoryId}/inactive")
//     public ResponseEntity<Void> toInactivate(@PathVariable Long categoryId) {
//         ChildCategory category = categoryService.findOrFail(categoryId);

//         if (!securityUtils.canChangeCategories(category.getUser().getId())) {
//             throw new AccessDeniedException("Você não tem permissão para realizar determinada ação.");
//         }

//         categoryService.toInactivate(categoryId);

//         return ResponseEntity.noContent().build();
//     }
// }