package org.valor.model.dto;

import org.valor.model.enums.Priority;
import org.valor.model.enums.Status;

import java.time.LocalDateTime;

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Status status; // TODO, IN_PROGRESS, DONE
    private Priority priority;
    private CategoryResponse category;
    private RecurrenceResponse recurrence;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
