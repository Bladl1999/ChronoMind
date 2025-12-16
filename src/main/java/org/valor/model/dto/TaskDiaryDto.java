package org.valor.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.valor.enums.PriorityTaskEnum;

import java.time.Instant;
import java.util.UUID;

public class TaskDiaryDto {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("taskNote")
    private String taskNote;
    @JsonProperty("priority")
    private PriorityTaskEnum priority;
    @JsonProperty("starTask")
    private Instant starTask;
    @JsonProperty("finishTask")
    private Instant finishTask;

    public TaskDiaryDto() {
    }

    public TaskDiaryDto(
            UUID id,
            String name,
            String taskNote,
            PriorityTaskEnum priority,
            Instant starTask,
            Instant finishTask
    ) {
        this.id = id;
        this.name = name;
        this.taskNote = taskNote;
        this.priority = priority;
        this.starTask = starTask;
        this.finishTask = finishTask;
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

    public PriorityTaskEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityTaskEnum priority) {
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
}
