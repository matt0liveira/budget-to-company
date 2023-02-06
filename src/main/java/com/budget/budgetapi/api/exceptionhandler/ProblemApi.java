package com.budget.budgetapi.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "ProblemApi")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class ProblemApi {
    private Integer status;
    private String type;
    private String title;
    private String detail;
    private OffsetDateTime timestamp;
    private String userMessage;
    private List<Object> objects;

    @Getter
    @Builder
    public static class Object {
        private String name;
        private String userMessage;
    }
}
