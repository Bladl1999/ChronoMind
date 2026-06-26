package org.valor.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.valor.enums.PriorityTaskEnum;
import org.valor.model.dto.TaskDiaryDto;
import org.valor.model.dto.TaskDiaryUpdateRequest;

import java.time.Instant;

@Entity
@Table(name = "task_diary")
public class TaskDiary extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "task_note")
    private String taskNote;
    @Column(name = "priority")
    private Integer priority;
    @Column(name = "start_task")
    private Instant starTask;
    @Column(name = "finish_task")
    private Instant finishTask;

    public TaskDiary() {
    }

    public TaskDiary(TaskDiaryDto request) {
        this.name = request.name();
        this.taskNote = request.taskNote();
        this.priority = request.priority().getPriority();
        this.starTask = request.starTask();
        this.finishTask = request.finishTask();
    }

    public TaskDiary update(TaskDiaryUpdateRequest request) {
        request.name().ifPresent(this::setName);
        request.taskNote().ifPresent(this::setTaskNote);
        request.priority().ifPresent(this::setPriority);
        request.starTask().ifPresent(this::setStarTask);
        request.finishTask().ifPresent(this::setFinishTask);
        setUpdateTimestamp(Instant.now());
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskNote() {
        return taskNote;
    }

    public void setTaskNote(String taskNote) {
        this.taskNote = taskNote;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setPriority(PriorityTaskEnum priorityTaskEnum) {
        this.priority = priorityTaskEnum.getPriority();
    }

    public Instant getStarTask() {
        return starTask;
    }

    public void setStarTask(Instant starTask) {
        this.starTask = starTask;
    }

    public Instant getFinishTask() {
        return finishTask;
    }

    public void setFinishTask(Instant finishTask) {
        this.finishTask = finishTask;
    }
}
