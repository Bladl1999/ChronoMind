package org.valor.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category extends BaseEntity{

    private String name;
    private Long userId; // владелец
}
