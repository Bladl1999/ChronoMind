package org.valor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthorRequest (
    @JsonProperty("username") String name,
    @JsonProperty("password") String password
){}

