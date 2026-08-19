package com.codurance.training.tasks.adapter.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.codurance.training.tasks.io.springboot.config.UseCaseInjection;
import com.codurance.training.tasks.usecase.port.in.todolist.help.HelpOutput;
import com.codurance.training.tasks.usecase.port.in.todolist.help.HelpUseCase;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@AutoConfigureAfter({ UseCaseInjection.class })
public class HelpController {
	private final HelpUseCase helpUseCase;

	@Autowired
	public HelpController(HelpUseCase helpUseCase) {
		this.helpUseCase = helpUseCase;
	}

	@GetMapping("/help")
	public ResponseEntity<HelpOutput> help() {
		HelpOutput helpOutput = helpUseCase.execute();

		return new ResponseEntity<>(helpOutput, HttpStatus.OK);
	}

}
