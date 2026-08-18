package com.codurance.training.tasks.adapter.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.codurance.training.tasks.adapter.presenter.HelpConsolePresenter;
import com.codurance.training.tasks.io.springboot.config.UseCaseInjection;
import com.codurance.training.tasks.usecase.port.in.todolist.help.HelpOutput;
import com.codurance.training.tasks.usecase.port.in.todolist.help.HelpUseCase;
import com.codurance.training.tasks.usecase.port.out.todolist.help.HelpPresenter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AutoConfigureAfter({ UseCaseInjection.class })
public class HelpController {
	private final HelpUseCase helpUseCase;
	private final HelpPresenter helpPresenter;

	@Autowired
	public HelpController(HelpUseCase helpUseCase,
			@Qualifier("webHelp") HelpPresenter helpPresenter) {
		this.helpUseCase = helpUseCase;
		this.helpPresenter = helpPresenter;
	}

	@GetMapping("/help")
	public ResponseEntity<HelpOutput> help() {
		HelpOutput helpOutput = helpUseCase.execute();

		return new ResponseEntity<>(helpOutput, HttpStatus.OK);
	}

}
