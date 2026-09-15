package org.valor.model.dto;

public record ChangePasswordRequest(
    String currentPassword,
    String newPassword
){}
