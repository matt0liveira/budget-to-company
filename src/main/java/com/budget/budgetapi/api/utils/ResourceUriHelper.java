package com.budget.budgetapi.api.utils;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceUriHelper {

    public static URI addUriInResponseHeader(Object resourceId) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(resourceId)
                .toUri();
    }
}
