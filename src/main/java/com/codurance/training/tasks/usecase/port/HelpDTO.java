package com.codurance.training.tasks.usecase.port;

import java.util.ArrayList;
import java.util.List;

public class HelpDTO {
    public String heading;
    public List<String> commands;

    public HelpDTO() {
        this.commands = new ArrayList<>();
    }
}
