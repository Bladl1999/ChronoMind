package org.valor.model.dto;

public record CategoryResponse(
        Long id,
        String name,
        String color // опционально, hex-код цвета
) {
}
