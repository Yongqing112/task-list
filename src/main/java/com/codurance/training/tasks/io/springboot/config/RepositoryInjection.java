package com.codurance.training.tasks.io.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepository;
import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepositoryPeer;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;

@PropertySource(value = "classpath:/application.properties")
@Configuration("ToDoListRepositoryInjection")
public class RepositoryInjection {
	
	@Bean(name = "toDoListRepository")
	public ToDoListRepository toDoListRepository() {
		return new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
	}
}
