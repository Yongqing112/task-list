package com.codurance.training.tasks.adapter.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.codurance.training.tasks.io.springboot.config.UseCaseInjection;
import com.codurance.training.tasks.io.standard.ToDoListApp;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectInput;
import com.codurance.training.tasks.usecase.port.in.project.add.AddProjectUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AutoConfigureAfter({ UseCaseInjection.class })
public class AddProjectController {
	private final AddProjectUseCase addProjectUseCase;

	@Autowired
	public AddProjectController(AddProjectUseCase addProjectUseCase) {
		this.addProjectUseCase = addProjectUseCase;
	}

	@PostMapping("/projects")
	public ResponseEntity<String> addProject(@RequestParam("toDoListId") String toDoListId,
			@RequestParam("projectName") String projectName) {
		if (toDoListId == null || toDoListId.isEmpty()) {
			return new ResponseEntity<>("toDoListId is null or empty", HttpStatus.BAD_REQUEST);
		}

		if (projectName == null || projectName.isEmpty()) {
			return new ResponseEntity<>("projectName is null or empty", HttpStatus.BAD_REQUEST);
		}

		AddProjectInput addProjectInput = new AddProjectInput();
		addProjectInput.toDoListId = toDoListId;
		addProjectInput.projectName = projectName;
		addProjectUseCase.execute(addProjectInput);

		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

}
