package org.valor.model.entity;

import jakarta.persistence.*;
import org.apache.catalina.User;
import org.valor.model.enums.SessionType;

import java.time.LocalDateTime;

@Entity
@Table(name = "pomodoro_sessions")
public class PomodoroSession extends BaseEntity{
    @ManyToOne
    private Users user;
    @ManyToOne
    private Task task; // может быть null
    @Enumerated(EnumType.STRING)
    private SessionType type;
    private Integer durationMinutes;
    private LocalDateTime completedAt;
}
