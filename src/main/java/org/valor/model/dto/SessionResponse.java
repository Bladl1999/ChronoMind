package org.valor.model.dto;

import org.valor.model.enums.SessionType;

import java.time.LocalDateTime;

public class SessionResponse {
    private Long id;
    private Long taskId;
    private String taskName;
    private SessionType type;
    private Integer durationMinutes;
    private LocalDateTime completedAt;
}
