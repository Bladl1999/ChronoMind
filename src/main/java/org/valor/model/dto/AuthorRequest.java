package org.valor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthorRequest (
    @JsonProperty("email") String email,
    @JsonProperty("password") String password
){}

