package com.codurance.training.tasks.usecase.port;

import java.util.ArrayList;
import java.util.List;

public class ToDoListDTO {

    public String toDoListId;

    public List<ProjectDTO> projectDTOs;

    public ToDoListDTO() {
        this.projectDTOs = new ArrayList<>();
    }

}
