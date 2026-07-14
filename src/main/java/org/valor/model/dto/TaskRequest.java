package org.valor.model.dto;

import org.valor.model.enums.Priority;

import java.time.LocalDateTime;

public class TaskRequest {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Priority priority; // enum LOW, MEDIUM, HIGH
    private Long categoryId;
    private RecurrenceRequest recurrence;
    private Long version; // для PUT/PATCH
}
