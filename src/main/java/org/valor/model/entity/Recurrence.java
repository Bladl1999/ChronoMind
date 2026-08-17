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

    public Recurrence() {
    }

    public Recurrence(
            RecurrenceType type,
            Integer interval,
            LocalDate endDate
    ) {
        this.type = type;
        this.interval = interval;
        this.endDate = endDate;
    }

    public RecurrenceType getType() {
        return type;
    }

    public void setType(RecurrenceType type) {
        this.type = type;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
