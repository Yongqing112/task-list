package com.codurance.training.tasks.usecase;

import java.io.PrintWriter;
import java.util.List;

import com.codurance.training.tasks.TaskList;
import com.codurance.training.tasks.entity.ProjectName;
import com.codurance.training.tasks.entity.Task;
import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectInput;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;
import com.codurance.training.tasks.usecase.service.AddProjectService;

public class Add {

    private final ToDoList toDoList;
    private final PrintWriter out;
    private final ToDoListRepository repository;

    public Add(ToDoList toDoList, PrintWriter out, ToDoListRepository repository) {
        this.toDoList = toDoList;
        this.out = out;
        this.repository = repository;
    }

    public void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            AddProjectUseCase addProjectUseCase = new AddProjectService(repository);
            AddProjectInput addProjectInput = new AddProjectInput();
            addProjectInput.toDoListId = TaskList.DEFAULT_TO_DO_LIST_ID;
            addProjectInput.projectName = subcommandRest[1];
            addProjectUseCase.execute(addProjectInput);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            addTask(ProjectName.of(projectTask[0]), projectTask[1]);
        }
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
