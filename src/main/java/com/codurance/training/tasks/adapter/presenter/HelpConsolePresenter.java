package com.codurance.training.tasks.adapter.presenter;

import java.io.PrintWriter;

import com.codurance.training.tasks.usecase.port.HelpDTO;
import com.codurance.training.tasks.usecase.port.out.todolist.help.HelpPresenter;

public class HelpConsolePresenter implements HelpPresenter {

    private final PrintWriter out;

    public HelpConsolePresenter(PrintWriter out) {
        this.out = out;
    }

    public void present(HelpDTO helpDTO) {

        out.println(helpDTO.heading);
        for (var command : helpDTO.commands)
            out.printf("  %s%n", command);
        out.println();
    }

}
