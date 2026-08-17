package org.valor.mapper;

import org.valor.model.dto.CategoryResponse;
import org.valor.model.entity.Category;

public class CategoryMapper {
    public static CategoryResponse toDto(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                "Green" //TODO заменить на нормальный цвет
        );
    }
}
