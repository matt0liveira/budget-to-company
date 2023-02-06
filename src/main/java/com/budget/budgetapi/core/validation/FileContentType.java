package com.budget.budgetapi.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
        ElementType.PARAMETER, ElementType.TYPE_USE })
@Constraint(validatedBy = { FileContentTypeValidator.class })
public @interface FileContentType {

    String message() default "Tipo de arquivo inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] allowed();
}
