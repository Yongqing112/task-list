package com.codurance.training.tasks.usecase;

import java.io.PrintWriter;

import com.codurance.training.tasks.entity.Project;
import com.codurance.training.tasks.entity.Task;
import com.codurance.training.tasks.entity.ToDoList;

public class Show {

    private final ToDoList toDoList;
    private final PrintWriter out;

    public Show(ToDoList toDoList, PrintWriter out) {
        this.toDoList = toDoList;
        this.out = out;
    }

    public void show() {
        for (Project project : toDoList.getProjects()) {
            out.println(project.getProjectName());
            for (Task task : project.getTasks()) {
                out.printf("    [%c] %s: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }

}
