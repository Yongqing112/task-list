package com.codurance.training.tasks.usecase.port;

import com.codurance.training.tasks.entity.ToDoList;

public class ToDoListMapper {

    public static ToDoListDTO toDTO(ToDoList toDoList) {
        ToDoListDTO toDoListDTO = new ToDoListDTO();
        toDoListDTO.toDoListId = toDoList.geToDoListId().value();
        toDoListDTO.projectDTOs = ProjectMapper.toDTO(toDoList.getProjects());
        return toDoListDTO;
    }
}
