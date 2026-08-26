package com.codurance.training.tasks.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;

import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepository;
import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepositoryPeer;
import com.codurance.training.tasks.adapter.repository.ToDoListRepositoryPeer;
import com.codurance.training.tasks.io.springboot.ToDoListSpringBootApp;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectUseCase;
import com.codurance.training.tasks.usecase.port.in.task.add.AddTaskUseCase;
import com.codurance.training.tasks.usecase.port.in.task.setDone.SetDoneUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.error.ErrorUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.help.HelpUseCase;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowUseCase;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;
import com.codurance.training.tasks.usecase.service.AddProjectService;
import com.codurance.training.tasks.usecase.service.AddTaskService;
import com.codurance.training.tasks.usecase.service.ErrorService;
import com.codurance.training.tasks.usecase.service.HelpService;
import com.codurance.training.tasks.usecase.service.SetDoneTaskService;
import com.codurance.training.tasks.usecase.service.ShowService;

@ComponentScan(basePackages = { "com.codurance.training.tasks" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ToDoListSpringBootApp.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = com.codurance.SpringBootApplicationTest.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = com.codurance.training.tasks.io.springboot.config.RepositoryInjection.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = com.codurance.training.tasks.io.springboot.config.UseCaseInjection.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = com.codurance.training.tasks.io.springboot.config.ToDoListDataSourceConfiguration.class) })
@EntityScan(basePackages = { "com.codurance.training.tasks" })

@SpringBootApplication(exclude = { MongoAutoConfiguration.class })
public abstract class JpaApplicationTestContext {

	@Bean
	@Primary
	public ToDoListRepository toDoListRepository(ToDoListRepositoryPeer toDoListRepositoryPeer) {
		return new ToDoListInMemoryRepository(toDoListRepositoryPeer);
	}

	@Bean
	public AddProjectUseCase addProjectUseCase(ToDoListRepository toDoListRepository) {
		return new AddProjectService(toDoListRepository);
	}

	@Bean
	public AddTaskUseCase addTaskUseCase(ToDoListRepository toDoListRepository) {
		return new AddTaskService(toDoListRepository);
	}

	@Bean
	public SetDoneUseCase setDoneUseCase(ToDoListRepository toDoListRepository) {
		return new SetDoneTaskService(toDoListRepository);
	}

	@Bean
	public ErrorUseCase errorUseCase() {
		return new ErrorService();
	}

	@Bean
	public ShowUseCase showUseCase(ToDoListRepository toDoListRepository) {
		return new ShowService(toDoListRepository);
	}

	@Bean
	public HelpUseCase helpUseCase() {
		return new HelpService();
	}
}
