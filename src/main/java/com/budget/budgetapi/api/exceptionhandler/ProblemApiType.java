package com.budget.budgetapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemApiType {
    RESOURCE_NOT_FOUND("Recurso não encontrado", "/recurso-nao-encontrado"),
    DOMAIN("Violação de regra de negócio", "/erro-dominio"),
    METHOD_ARGUMENT_TYPE_MISMATCH("Tipo de parâmetro imcompatível", "/parametro-imcompativel"),
    INVALID_DATA("Dados inválidos", "/dados-invalidos"),
    INVALID_PARAMETER("Parâmetros inválidos", "/parametros-invalidos"),
    MEDIA_TYPE_NOT_SUPPORTED("Tipo de mídia não suportada", "/tipo-de-midia-nao-suportada"),
    ENTITY_IN_USE("Entidade em uso", "/entidade-em-uso"),
    FORBIDDEN("Acesso negado", "/acesso-negado");

    private String title;
    private String uri;

    private ProblemApiType(String title, String path) {
        this.title = title;
        this.uri = "https://budget.com/kb" + path;
    }
}