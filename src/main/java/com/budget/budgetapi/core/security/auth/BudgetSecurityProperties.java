package com.budget.budgetapi.core.security.auth;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("budget.auth")
@Setter
@Getter
@Component
@Validated
public class BudgetSecurityProperties {

    @NotBlank
    private String providerUrl;
}
