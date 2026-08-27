package com.codurance.training.tasks.adapter.controller.console;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.codurance.training.tasks.io.standard.ToDoListApp;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectInput;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectUseCase;
import com.codurance.training.tasks.usecase.port.in.task.add.AddTaskInput;
import com.codurance.training.tasks.usecase.port.in.task.add.AddTaskUseCase;
import com.codurance.training.tasks.usecase.port.in.task.deadline.DeadlineInput;
import com.codurance.training.tasks.usecase.port.in.task.deadline.DeadlineUseCase;
import com.codurance.training.tasks.usecase.port.in.task.setDone.SetDoneInput;
import com.codurance.training.tasks.usecase.port.in.task.setDone.SetDoneUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.error.ErrorInput;
import com.codurance.training.tasks.usecase.port.in.todolist.error.ErrorUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.help.HelpOutput;
import com.codurance.training.tasks.usecase.port.in.todolist.help.HelpUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowInput;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowOutput;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowUseCase;
import com.codurance.training.tasks.usecase.port.out.todolist.help.HelpPresenter;
import com.codurance.training.tasks.usecase.port.out.todolist.show.ShowPresenter;

public class ToDoListConsoleController {
    private final PrintWriter out;
    private final ShowUseCase showUseCase;
    private final ShowPresenter showPresenter;
    private final AddProjectUseCase addProjectUseCase;
    private final AddTaskUseCase addTaskUseCase;
    private final SetDoneUseCase setDoneUseCase;
    private final HelpUseCase helpUseCase;
    private final HelpPresenter helpPresenter;
    private final DeadlineUseCase deadlineUseCase;
    private final ErrorUseCase errorUseCase;

    public ToDoListConsoleController(PrintWriter out,
            ShowUseCase showUseCase,
            ShowPresenter showPresenter,
            AddProjectUseCase addProjectUseCase,
            AddTaskUseCase addTaskUseCase,
            SetDoneUseCase setDoneUseCase,
            HelpUseCase helpUseCase,
            HelpPresenter helpPresenter,
            DeadlineUseCase deadlineUseCase,
            ErrorUseCase errorUseCase) {
        this.out = out;
        this.showUseCase = showUseCase;
        this.showPresenter = showPresenter;
        this.addProjectUseCase = addProjectUseCase;
        this.addTaskUseCase = addTaskUseCase;
        this.setDoneUseCase = setDoneUseCase;
        this.helpUseCase = helpUseCase;
        this.helpPresenter = helpPresenter;
        this.deadlineUseCase = deadlineUseCase;
        this.errorUseCase = errorUseCase;
    }

    public void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                show();
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
                help();
                break;
            case "deadline":
                deadline(commandRest[1]);
                break;
            default:
                error(command);
                break;
        }
    }

    private void show() {
        ShowInput showInput = new ShowInput();
        showInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        ShowOutput showOutput = showUseCase.execute(showInput);
        showPresenter.present(showOutput.toDoListDTO);
    }

    private void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            AddProjectInput addProjectInput = new AddProjectInput();
            addProjectInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
            addProjectInput.projectName = subcommandRest[1];
            addProjectUseCase.execute(addProjectInput);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            AddTaskInput addTaskInput = new AddTaskInput();
            addTaskInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
            addTaskInput.projectName = projectTask[0];
            addTaskInput.description = projectTask[1];
            addTaskInput.done = false;
            addTaskUseCase.execute(addTaskInput);
            out.print(addTaskUseCase.getMessage());
        }
    }

    private void setDone(String taskId, boolean done) {
        SetDoneInput setDoneInput = new SetDoneInput();
        setDoneInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        setDoneInput.taskId = taskId;
        setDoneInput.done = done;

        setDoneUseCase.execute(setDoneInput);
        out.print(setDoneUseCase.getMessage());
    }

    private void help() {
        HelpOutput helpOutput = helpUseCase.execute();
        helpPresenter.present(helpOutput.helpDTO);
    }

    private void deadline(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        DeadlineInput deadlineInput = new DeadlineInput();
        deadlineInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        deadlineInput.taskId = subcommandRest[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = subcommandRest[1] + " 00:00:00";
        deadlineInput.deadline = LocalDateTime.parse(dateTimeString, formatter);
        deadlineUseCase.execute(deadlineInput);
        out.print(deadlineUseCase.getMessage());
    }

    private void error(String command) {
        ErrorInput errorInput = new ErrorInput();
        errorInput.command = command;
        errorUseCase.execute(errorInput);
        out.print(errorUseCase.getMessage());
    }

}
