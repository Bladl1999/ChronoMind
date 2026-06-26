package org.valor.model.dto;

import org.valor.model.entity.Users;

import java.util.Optional;
import java.util.UUID;

public record UsersDto(
        Optional<UUID> id,
        Optional<String> login,
        Optional<String> password
) {
    public static UsersDto fromEntity(Users users) {
        return new UsersDto(
                Optional.ofNullable(users.getId()),
                Optional.ofNullable(users.getLogin()),
                Optional.of(null)
        );
    }
}
