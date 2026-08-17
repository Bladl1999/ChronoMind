package org.valor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterRequest(
        @JsonProperty(value = "username") String name,
        @JsonProperty(value = "email") String email,
        @JsonProperty(value = "password") String password
) {
}
