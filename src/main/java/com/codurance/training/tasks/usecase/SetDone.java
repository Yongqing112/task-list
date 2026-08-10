package com.codurance.training.tasks.usecase;

import java.io.PrintWriter;

import com.codurance.training.tasks.entity.Project;
import com.codurance.training.tasks.entity.Task;
import com.codurance.training.tasks.entity.TaskId;
import com.codurance.training.tasks.entity.ToDoList;

public class SetDone {
    private final ToDoList toDoList;
    private final PrintWriter out;

    public SetDone(ToDoList toDoList, PrintWriter out) {
        this.toDoList = toDoList;
        this.out = out;
    }

    public void setDone(String idString, boolean done) {
        TaskId id = TaskId.of(idString);
        for (Project project : toDoList.getProjects()) {
            for (Task task : project.getTasks()) {
                if (task.getId().equals(id)) {
                    toDoList.setDone(task.getId(), done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %s.", id);
        out.println();
    }

}
