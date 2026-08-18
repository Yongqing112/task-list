package com.codurance.training.tasks.io.springboot.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codurance.training.tasks.adapter.presenter.HelpConsolePresenter;
import com.codurance.training.tasks.adapter.presenter.ShowConsolePresenter;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectUseCase;
import com.codurance.training.tasks.usecase.port.in.task.add.AddTaskUseCase;
import com.codurance.training.tasks.usecase.port.in.task.setDone.SetDoneUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.error.ErrorUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.help.HelpUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;
import com.codurance.training.tasks.usecase.port.out.todolist.help.HelpPresenter;
import com.codurance.training.tasks.usecase.port.out.todolist.show.ShowPresenter;
import com.codurance.training.tasks.usecase.service.AddProjectService;
import com.codurance.training.tasks.usecase.service.AddTaskService;
import com.codurance.training.tasks.usecase.service.ErrorService;
import com.codurance.training.tasks.usecase.service.HelpService;
import com.codurance.training.tasks.usecase.service.SetDoneTaskService;
import com.codurance.training.tasks.usecase.service.ShowService;

@Configuration("UseCaseInjection")
@AutoConfigureAfter({ RepositoryInjection.class })
public class UseCaseInjection {
	private final ToDoListRepository toDoListRepository;

	@Autowired
	public UseCaseInjection(ToDoListRepository toDoListRepository) {
		this.toDoListRepository = toDoListRepository;
	}

	@Bean
	public BufferedReader getIn() {
		return new BufferedReader(new InputStreamReader(System.in));
	}

	@Bean
	public PrintWriter getOut() {
		return new PrintWriter(System.out);
	}

	@Bean
	public AddProjectUseCase addProjectUseCase() {
		return new AddProjectService(toDoListRepository);
	}

	@Bean
	public AddTaskUseCase addTaskUseCase() {
		return new AddTaskService(toDoListRepository);
	}

	@Bean
	public SetDoneUseCase setDoneUseCase() {
		return new SetDoneTaskService(toDoListRepository);
	}

	@Bean
	public ErrorUseCase errorUseCase() {
		return new ErrorService();
	}

	@Bean
	public ShowUseCase showUseCase() {
		return new ShowService(toDoListRepository);
	}

	@Bean
	public HelpUseCase helpUseCase() {
		return new HelpService();
	}

	@Bean
	public HelpPresenter helpPresenter() {
		return new HelpConsolePresenter(getOut());
	}

	@Bean
	public ShowPresenter showPresenter() {
		return new ShowConsolePresenter(getOut());
	}
}
