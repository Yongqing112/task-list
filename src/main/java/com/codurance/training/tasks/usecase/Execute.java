package com.codurance.training.tasks.usecase;

import java.io.PrintWriter;

import com.codurance.training.tasks.TaskList;
import com.codurance.training.tasks.adapter.presenter.ShowConsolePresenter;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectInput;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectUseCase;
import com.codurance.training.tasks.usecase.port.in.project.task.add.AddTaskInput;
import com.codurance.training.tasks.usecase.port.in.project.task.add.AddTaskUseCase;
import com.codurance.training.tasks.usecase.port.in.project.task.setDone.SetDoneInput;
import com.codurance.training.tasks.usecase.port.in.project.task.setDone.SetDoneUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowInput;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowOutput;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;
import com.codurance.training.tasks.usecase.port.out.todolist.show.ShowPresenter;
import com.codurance.training.tasks.usecase.service.AddProjectService;
import com.codurance.training.tasks.usecase.service.AddTaskService;
import com.codurance.training.tasks.usecase.service.SetDoneTaskService;
import com.codurance.training.tasks.usecase.service.ShowService;

public class Execute {
    private final PrintWriter out;
    private final ToDoListRepository repository;

    public Execute(PrintWriter out, ToDoListRepository repository) {
        this.out = out;
        this.repository = repository;
    }

    public void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                show();
                ;
                break;
            case "add":
                add(commandRest[1]);
                break;
            case "check":
                setDone(commandRest[1], true);
                break;
            case "uncheck":
                setDone(commandRest[1], false);
                break;
            case "help":
                new Help(out).help();
                break;
            default:
                new Error(out).error(command);
                break;
        }
    }

    private void show() {
        ShowUseCase showUseCase = new ShowService(repository);
        ShowInput showInput = new ShowInput();
        showInput.toDoListId = TaskList.DEFAULT_TO_DO_LIST_ID;
        ShowOutput showOutput = showUseCase.execute(showInput);
        ShowPresenter showPresenter = new ShowConsolePresenter(out);
        showPresenter.present(showOutput.toDoListDTO);

    }

    private void add(String commandLine) {
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
            AddTaskUseCase addTaskUseCase = new AddTaskService(repository);
            AddTaskInput addTaskInput = new AddTaskInput();
            addTaskInput.toDoListId = TaskList.DEFAULT_TO_DO_LIST_ID;
            addTaskInput.projectName = projectTask[0];
            addTaskInput.description = projectTask[1];
            addTaskInput.done = false;
            addTaskUseCase.execute(addTaskInput);
            out.print(addTaskUseCase.getMessage());
        }
    }

    private void setDone(String taskId, boolean done) {
        SetDoneInput setDoneInput = new SetDoneInput();
        setDoneInput.toDoListId = TaskList.DEFAULT_TO_DO_LIST_ID;
        setDoneInput.taskId = taskId;
        setDoneInput.done = done;

        SetDoneUseCase setDoneUseCase = new SetDoneTaskService(repository);
        setDoneUseCase.execute(setDoneInput);
        out.print(setDoneUseCase.getMessage());
    }

}
