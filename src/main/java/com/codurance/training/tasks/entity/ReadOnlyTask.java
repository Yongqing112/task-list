package com.codurance.training.tasks.entity;

import java.time.LocalDateTime;

public class ReadOnlyTask extends Task {

    ReadOnlyTask(Task task) {
        super(task.getId(), task.getDescription(), task.isDone(), task.getDeadline());
    }

    public void setDone(boolean done) {
        throw new UnsupportedOperationException("Read Only");
    }

    public void setDeadline(LocalDateTime deadline) {
        throw new UnsupportedOperationException("Read Only");
    }
}
