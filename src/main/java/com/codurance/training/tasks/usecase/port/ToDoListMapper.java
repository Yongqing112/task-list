package com.codurance.training.tasks.usecase.port;

import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.out.ToDoListPO;

public class ToDoListMapper {

    public static ToDoListDTO toDTO(ToDoList toDoList) {
        ToDoListDTO toDoListDTO = new ToDoListDTO();
        toDoListDTO.toDoListId = toDoList.geToDoListId().value();
        toDoListDTO.projectDTOs = ProjectMapper.toDTO(toDoList.getProjects());
        return toDoListDTO;
    }

    public static ToDoList toDomain(ToDoListPO toDoListPO) {
        return new ToDoList(ToDoListId.of(toDoListPO.getId()),
                toDoListPO.getLastTaskId(),
                ProjectMapper.toDomain(toDoListPO.getProjectPOs()));
    }

    public static ToDoListPO toPO(ToDoList toDoList) {
        ToDoListPO toDoListPO = new ToDoListPO(toDoList.geToDoListId().value(), toDoList.getLastTaskId());
        toDoListPO.setProjectPOs(ProjectMapper.toPO(toDoList.getProjects()));
        return toDoListPO;
    }
}
