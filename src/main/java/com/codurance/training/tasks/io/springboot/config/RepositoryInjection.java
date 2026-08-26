package com.codurance.training.tasks.io.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.codurance.training.tasks.adapter.repository.ToDoListCrudRepository;
import com.codurance.training.tasks.adapter.repository.ToDoListCrudRepositoryPeer;
import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepository;
import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepositoryPeer;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;

@PropertySource(value = "classpath:/application.properties")
@Configuration("ToDoListRepositoryInjection")
public class RepositoryInjection {

	private ToDoListInMemoryRepositoryPeer toDoListInMemoryRepositoryPeer;
	private ToDoListCrudRepositoryPeer toDoListCrudRepositoryPeer;

	@Autowired
	public RepositoryInjection(ToDoListInMemoryRepositoryPeer toDoListInMemoryRepositoryPeer,
			ToDoListCrudRepositoryPeer toDoListCrudRepositoryPeer) {
		this.toDoListInMemoryRepositoryPeer = toDoListInMemoryRepositoryPeer;
		this.toDoListCrudRepositoryPeer = toDoListCrudRepositoryPeer;
	}

	@Bean(name = "toDoListRepository")
	public ToDoListRepository toDoListRepository() {
		return new ToDoListInMemoryRepository(toDoListInMemoryRepositoryPeer);
	}

	@Bean(name = "toDoListCrudRepository")
	public ToDoListRepository toDoListCrudRepository() {
		return new ToDoListCrudRepository(toDoListCrudRepositoryPeer);
	}
}
