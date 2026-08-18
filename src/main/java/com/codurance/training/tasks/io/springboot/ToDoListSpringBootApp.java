package com.codurance.training.tasks.io.springboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import com.codurance.training.tasks.adapter.controller.console.ToDoListConsoleController;
import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.io.springboot.config.UseCaseInjection;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectUseCase;
import com.codurance.training.tasks.usecase.port.in.task.add.AddTaskUseCase;
import com.codurance.training.tasks.usecase.port.in.task.setDone.SetDoneUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.error.ErrorUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.help.HelpUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;
import com.codurance.training.tasks.usecase.port.out.todolist.help.HelpPresenter;
import com.codurance.training.tasks.usecase.port.out.todolist.show.ShowPresenter;

@ComponentScan(basePackages = "com.codurance.training.tasks")
@EntityScan(basePackages = "com.codurance.training.tasks")
@AutoConfigureAfter({ UseCaseInjection.class })
@SpringBootApplication
public class ToDoListSpringBootApp extends SpringBootServletInitializer implements CommandLineRunner {

	private static final String QUIT = "quit";

	public static final String DEFAULT_TO_DO_LIST_ID = "001";

	private final BufferedReader in;
	private final PrintWriter out;
	private final ShowUseCase showUseCase;
	private final ShowPresenter showPresenter;
	private final AddProjectUseCase addProjectUseCase;
	private final AddTaskUseCase addTaskUseCase;
	private final SetDoneUseCase setDoneUseCase;
	private final HelpUseCase helpUseCase;
	private final HelpPresenter helpPresenter;
	private final ErrorUseCase errorUseCase;

	public static void main(String[] args) {
		SpringApplication.run(ToDoListSpringBootApp.class, args);
	}

	public ToDoListSpringBootApp(
			BufferedReader reader,
			PrintWriter writer,
			ToDoListRepository repository,
			ShowUseCase showUseCase,
			ShowPresenter showPresenter,
			AddProjectUseCase addProjectUseCase,
			AddTaskUseCase addTaskUseCase,
			SetDoneUseCase setDoneUseCase,
			HelpUseCase helpUseCase,
			@Qualifier("consoleHelp") HelpPresenter helpPresenter,
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
		this.errorUseCase = errorUseCase;
		repository.save(new ToDoList(ToDoListId.of(DEFAULT_TO_DO_LIST_ID)));
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ToDoListSpringBootApp.class);
	}

	public void run(String... args) {
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
				System.exit(0);
			}
			new ToDoListConsoleController(out,
					showUseCase,
					showPresenter,
					addProjectUseCase,
					addTaskUseCase,
					setDoneUseCase,
					helpUseCase,
					helpPresenter,
					errorUseCase).execute(command);
		}
	}
}
