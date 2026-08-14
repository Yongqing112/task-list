package com.codurance.training.tasks.usecase.port.out.todolist.show;

import com.codurance.training.tasks.usecase.port.ToDoListDTO;

public interface ShowPresenter {
    void present(ToDoListDTO toDoListDTO);
}