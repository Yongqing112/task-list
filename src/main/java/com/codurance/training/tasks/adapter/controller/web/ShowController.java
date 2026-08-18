package com.codurance.training.tasks.adapter.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.codurance.training.tasks.io.springboot.config.UseCaseInjection;
import com.codurance.training.tasks.io.standard.ToDoListApp;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowInput;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowOutput;
import com.codurance.training.tasks.usecase.port.in.todolist.show.ShowUseCase;
import com.codurance.training.tasks.usecase.port.out.todolist.show.ShowPresenter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AutoConfigureAfter({ UseCaseInjection.class })
public class ShowController {
	private ShowUseCase showUseCase;
	private ShowPresenter showPresenter;

	@Autowired
	public ShowController(ShowUseCase showUseCase, ShowPresenter showPresenter) {
		this.showUseCase = showUseCase;
		this.showPresenter = showPresenter;
	}

	@GetMapping("/show")
	public ResponseEntity<String> show() {

		ShowInput showInput = new ShowInput();
		showInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
		ShowOutput showOutput = showUseCase.execute(showInput);
		return new ResponseEntity<>(showOutput.message, HttpStatus.OK);
	}

}
