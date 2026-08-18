package com.codurance.training.tasks.adapter.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.codurance.training.tasks.io.springboot.config.UseCaseInjection;
import com.codurance.training.tasks.io.standard.ToDoListApp;
import com.codurance.training.tasks.usecase.port.in.task.add.AddTaskInput;
import com.codurance.training.tasks.usecase.port.in.task.add.AddTaskUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AutoConfigureAfter({ UseCaseInjection.class })
public class AddTaskController {

	private final AddTaskUseCase addTaskUseCase;

	@Autowired
	public AddTaskController(AddTaskUseCase addTaskUseCase) {
		this.addTaskUseCase = addTaskUseCase;
	}

	@PostMapping("/tasks")
	public ResponseEntity<String> addTask(@RequestParam("toDoListId") String toDoListId,
			@RequestParam("projectName") String projectName,
			@RequestParam("description") String description) {

		if (toDoListId == null || toDoListId.isEmpty()) {
			return new ResponseEntity<>("toDoListId is null or empty", HttpStatus.BAD_REQUEST);
		}

		if (projectName == null || projectName.isEmpty()) {
			return new ResponseEntity<>("projectName is null or empty", HttpStatus.BAD_REQUEST);
		}

		if (description == null || description.isEmpty()) {
			return new ResponseEntity<>("description is null or empty", HttpStatus.BAD_REQUEST);
		}

		AddTaskInput addTaskInput = new AddTaskInput();
		addTaskInput.toDoListId = toDoListId;
		addTaskInput.projectName = projectName;
		addTaskInput.description = description;
		addTaskInput.done = false;
		addTaskUseCase.execute(addTaskInput);

		if (!addTaskUseCase.getMessage().isEmpty()) {
			return new ResponseEntity<>(addTaskUseCase.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

}
