package com.codurance.training.tasks.adapter.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.codurance.training.tasks.io.springboot.config.UseCaseInjection;
import com.codurance.training.tasks.usecase.port.in.task.setDone.SetDoneInput;
import com.codurance.training.tasks.usecase.port.in.task.setDone.SetDoneUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AutoConfigureAfter({ UseCaseInjection.class })
public class SetDoneController {
	private SetDoneUseCase setDoneUseCase;

	@Autowired
	public SetDoneController(SetDoneUseCase setDoneUseCase) {
		this.setDoneUseCase = setDoneUseCase;
	}

	@PostMapping("/setDone")
	public ResponseEntity<String> setDone(@RequestParam("toDoListId") String toDoListId,
			@RequestParam("taskId") String taskId, 
			@RequestParam("done") Boolean done) {

		if (toDoListId == null || toDoListId.isEmpty()) {
			return new ResponseEntity<>("toDoListId is null or empty", HttpStatus.BAD_REQUEST);
		}

		if (taskId == null || taskId.isEmpty()) {
			return new ResponseEntity<>("taskId is null or empty", HttpStatus.BAD_REQUEST);
		}

		if (done == null) {
			return new ResponseEntity<>("done is null", HttpStatus.BAD_REQUEST);
		}

		SetDoneInput setDoneInput = new SetDoneInput();
		setDoneInput.toDoListId = toDoListId;
		setDoneInput.taskId = taskId;
		setDoneInput.done = done;

		setDoneUseCase.execute(setDoneInput);

		if (!setDoneUseCase.getMessage().isEmpty()) {
			return new ResponseEntity<String>(setDoneUseCase.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}

}
