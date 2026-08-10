package com.codurance.training.tasks.usecase;

import java.io.PrintWriter;
import java.util.List;

import com.codurance.training.tasks.entity.ProjectName;
import com.codurance.training.tasks.entity.Task;
import com.codurance.training.tasks.entity.ToDoList;

public class Add {

    private final ToDoList toDoList;
    private final PrintWriter out;

    public Add(ToDoList toDoList, PrintWriter out) {
        this.toDoList = toDoList;
        this.out = out;
    }

    public void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            addProject(ProjectName.of(subcommandRest[1]));
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            addTask(ProjectName.of(projectTask[0]), projectTask[1]);
        }
    }

    private void addProject(ProjectName name) {
        toDoList.addProject(name);
    }

    private void addTask(ProjectName project, String description) {
        List<Task> projectTasks = toDoList.getTasks(project);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
            return;
        }
        toDoList.addTask(project, description, false);
    }
}
