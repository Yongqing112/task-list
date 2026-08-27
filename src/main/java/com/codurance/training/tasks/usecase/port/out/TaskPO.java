package com.codurance.training.tasks.usecase.port.out;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * TaskPO
 */
@Entity
@Table(name = "task")
public class TaskPO {

    @Id
    @Column(name = "id")
    private String taskId;
    @Column(name = "description")
    private String description;
    @Column(name = "done")
    private Boolean done;
    @Column(name = "deadline")
    private LocalDateTime deadline;

    public TaskPO() {
    }

    public TaskPO(String taskId, String description, Boolean done) {
        this(taskId, description, done, null);
    }

    public TaskPO(String taskId, String description, Boolean done, LocalDateTime deadline) {
        this.taskId = taskId;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
