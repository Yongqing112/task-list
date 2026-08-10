package com.codurance.training.tasks.entity;

public class ReadOnlyTask extends Task {

    ReadOnlyTask(Task task) {
        super(task.getId(), task.getDescription(), task.isDone());
    }

    public void setDone(boolean done) {
        throw new UnsupportedOperationException("Read Only");
    }
}
