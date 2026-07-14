package org.valor.model.entity;

import jakarta.persistence.*;
import org.valor.model.enums.RecurrenceType;

import java.time.LocalDate;

@Entity
public class Recurrence extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private RecurrenceType type; // DAILY, WEEKLY, MONTHLY
    private Integer interval;
    private LocalDate endDate;
}
