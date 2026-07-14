package org.valor.model.entity;

import jakarta.persistence.*;
import org.apache.catalina.User;

@Entity
@Table(name = "timer_settings")
public class TimerSettings extends BaseEntity{
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;
    private Integer workDurationMinutes;
    private Integer shortBreakMinutes;
    private Integer longBreakMinutes;
    private Integer sessionsBeforeLongBreak;
    private Boolean autoStartBreaks;
    private Boolean autoStartWork;
}
