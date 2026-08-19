package com.codurance.training.tasks.usecase.port.out;

/**
 * TaskPO
 */
public class TaskPO {

    private String taskId;
    private String description;
    private Boolean done;

    public TaskPO(String taskId, String description, Boolean done) {
        this.taskId = taskId;
        this.description = description;
        this.done = done;
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

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    

}
