package com.codurance.training.tasks.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.codurance.training.tasks.adapter.controller.ToDoListConsoleController;
import com.codurance.training.tasks.adapter.presenter.ShowConsolePresenter;
import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepository;
import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectUseCase;
import com.codurance.training.tasks.usecase.port.in.task.add.AddTaskUseCase;
import com.codurance.training.tasks.usecase.port.in.task.setDone.SetDoneUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.error.ErrorUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.help.HelpUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;
import com.codurance.training.tasks.usecase.port.out.todolist.show.ShowPresenter;
import com.codurance.training.tasks.usecase.service.AddProjectService;
import com.codurance.training.tasks.usecase.service.AddTaskService;
import com.codurance.training.tasks.usecase.service.ErrorService;
import com.codurance.training.tasks.usecase.service.HelpService;
import com.codurance.training.tasks.usecase.service.SetDoneTaskService;
import com.codurance.training.tasks.usecase.service.ShowService;

public final class ToDoListApp implements Runnable {
    private static final String QUIT = "quit";

    private final ToDoList toDoList = new ToDoList(ToDoListId.of(DEFAULT_TO_DO_LIST_ID));
    private final BufferedReader in;
    private final PrintWriter out;
    private final ToDoListRepository repository;
    private final ShowUseCase showUseCase;
    private final ShowPresenter showPresenter;
    private final AddProjectUseCase addProjectUseCase;
    private final AddTaskUseCase addTaskUseCase;
    private final SetDoneUseCase setDoneUseCase;
    private final HelpUseCase helpUseCase;
    private final ErrorUseCase errorUseCase;

    public static final String DEFAULT_TO_DO_LIST_ID = "001";

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new ToDoListApp(in, out).run();
    }

    public ToDoListApp(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
        repository = new ToDoListInMemoryRepository();
        if (repository.findById(ToDoListId.of(DEFAULT_TO_DO_LIST_ID)).isEmpty()) {
            repository.save(toDoList);
        }
        this.showUseCase = new ShowService(repository);
        this.showPresenter = new ShowConsolePresenter(out);
        this.addProjectUseCase = new AddProjectService(repository);
        this.addTaskUseCase = new AddTaskService(repository);
        this.setDoneUseCase = new SetDoneTaskService(repository);
        this.helpUseCase = new HelpService();
        this.errorUseCase = new ErrorService();
    }

    public void run() {
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            new ToDoListConsoleController(out,
                    showUseCase,
                    showPresenter,
                    addProjectUseCase,
                    addTaskUseCase,
                    setDoneUseCase,
                    helpUseCase,
                    errorUseCase).execute(command);
        }
    }

}
