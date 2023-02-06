package com.budget.budgetapi.api.model.input;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionInputModel {

    @NotNull
    @Positive
    private BigDecimal value;

    @NotBlank
    @Schema(name = "type", allowableValues = "EXPENSE,INCOME")
    private String type;

    @NotNull
    @Valid
    private CategoryRefInputModel category;

    @NotBlank
    private String description;

    @Schema(format = "date", type = "string")
    @DateTimeFormat(iso = ISO.DATE)
    private Date date;
}
