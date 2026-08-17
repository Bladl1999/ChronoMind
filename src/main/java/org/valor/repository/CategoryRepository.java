package org.valor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.valor.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
