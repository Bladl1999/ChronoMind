package org.valor.service.category;

import org.springframework.data.domain.Pageable;
import org.valor.model.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll(Pageable pageable);
}
