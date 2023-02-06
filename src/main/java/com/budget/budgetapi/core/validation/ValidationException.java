package com.budget.budgetapi.core.validation;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException {
    private BindingResult bindingResult;
}
