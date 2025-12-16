package org.valor.model.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "task_diary")
public class TaskDiary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
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
    @Column(name = "deleted")
    private Short deleted = 0;
    @Column(name = "create_timestamp")
    private Instant createTimestamp = Instant.now();
    @Column(name = "update_timestamp")
    private Instant updateTimestamp = Instant.now();
    @Column(name = "deleted_timestamp")
    private Instant deleteTimestamp;

    public TaskDiary() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }

    public Instant getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Instant createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Instant getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Instant updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Instant getDeleteTimestamp() {
        return deleteTimestamp;
    }

    public void setDeleteTimestamp(Instant deleteTimestamp) {
        this.deleteTimestamp = deleteTimestamp;
    }
}
