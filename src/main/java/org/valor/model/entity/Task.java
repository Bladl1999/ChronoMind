package org.valor.model.entity;

import jakarta.persistence.*;
import org.apache.catalina.User;
import org.valor.model.dto.CategoryDto;
import org.valor.model.enums.Priority;
import org.valor.model.enums.Status;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    private Users user;
    @Version
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToOne(cascade = CascadeType.ALL)
    private Recurrence recurrence;
    private Double estimatedHours; // плановое время в часах
    private LocalDateTime completedAt; // дата фактического выполнения (устанавливается при переходе в DONE)
}
