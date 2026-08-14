package com.codurance.training.tasks.usecase.service;

import com.codurance.training.tasks.usecase.port.HelpDTO;
import com.codurance.training.tasks.usecase.port.in.todolist.help.HelpOutput;
import com.codurance.training.tasks.usecase.port.in.todolist.help.HelpUseCase;

public class HelpService implements HelpUseCase {

    public HelpOutput execute() {
        HelpDTO helpDTO = new HelpDTO();
        helpDTO.heading = "Commands:";

        helpDTO.commands.add("show");
        helpDTO.commands.add("add project <project name>");
        helpDTO.commands.add("add task <project name> <task description>");
        helpDTO.commands.add("check <task ID>");
        helpDTO.commands.add("uncheck <task ID>");
        HelpOutput helpOutput = new HelpOutput();
        helpOutput.helpDTO = helpDTO;
        return helpOutput;
    }
}
