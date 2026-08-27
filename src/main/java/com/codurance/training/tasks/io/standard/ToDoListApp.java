package com.codurance.training.tasks.io.standard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.codurance.training.tasks.adapter.controller.console.ToDoListConsoleController;
import com.codurance.training.tasks.adapter.presenter.HelpConsolePresenter;
import com.codurance.training.tasks.adapter.presenter.ShowConsolePresenter;
import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepository;
import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepositoryPeer;
import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectUseCase;
import com.codurance.training.tasks.usecase.port.in.task.add.AddTaskUseCase;
import com.codurance.training.tasks.usecase.port.in.task.deadline.DeadlineUseCase;
import com.codurance.training.tasks.usecase.port.in.task.setDone.SetDoneUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.error.ErrorUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.help.HelpUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;
import com.codurance.training.tasks.usecase.port.out.todolist.help.HelpPresenter;
import com.codurance.training.tasks.usecase.port.out.todolist.show.ShowPresenter;
import com.codurance.training.tasks.usecase.service.AddProjectService;
import com.codurance.training.tasks.usecase.service.AddTaskService;
import com.codurance.training.tasks.usecase.service.DeadlineService;
import com.codurance.training.tasks.usecase.service.ErrorService;
import com.codurance.training.tasks.usecase.service.HelpService;
import com.codurance.training.tasks.usecase.service.SetDoneTaskService;
import com.codurance.training.tasks.usecase.service.ShowService;

public final class ToDoListApp implements Runnable {
    private static final String QUIT = "quit";

    private final BufferedReader in;
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

    public static final String DEFAULT_TO_DO_LIST_ID = "001";

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        ToDoListRepository repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        repository.save(new ToDoList(ToDoListId.of(DEFAULT_TO_DO_LIST_ID)));
        ShowUseCase showUseCase = new ShowService(repository);
        ShowPresenter showPresenter = new ShowConsolePresenter(out);
        AddProjectUseCase addProjectUseCase = new AddProjectService(repository);
        AddTaskUseCase addTaskUseCase = new AddTaskService(repository);
        SetDoneUseCase setDoneUseCase = new SetDoneTaskService(repository);
        HelpUseCase helpUseCase = new HelpService();
        HelpPresenter helpPresenter = new HelpConsolePresenter(out);
        DeadlineUseCase deadlineUseCase = new DeadlineService(repository);
        ErrorUseCase errorUseCase = new ErrorService();
        new ToDoListApp(
                in,
                out,
                showUseCase,
                showPresenter,
                addProjectUseCase,
                addTaskUseCase,
                setDoneUseCase,
                helpUseCase,
                helpPresenter,
                deadlineUseCase,
                errorUseCase).run();
    }

    public ToDoListApp(
            BufferedReader reader,
            PrintWriter writer,
            ShowUseCase showUseCase,
            ShowPresenter showPresenter,
            AddProjectUseCase addProjectUseCase,
            AddTaskUseCase addTaskUseCase,
            SetDoneUseCase setDoneUseCase,
            HelpUseCase helpUseCase,
            HelpPresenter helpPresenter,
            DeadlineUseCase deadlineUseCase,
            ErrorUseCase errorUseCase) {
        this.in = reader;
        this.out = writer;
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
                    helpPresenter,
                    deadlineUseCase,
                    errorUseCase).execute(command);
        }
    }

}
