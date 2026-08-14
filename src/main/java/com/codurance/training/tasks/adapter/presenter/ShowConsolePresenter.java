package com.codurance.training.tasks.adapter.presenter;

import java.io.PrintWriter;

import com.codurance.training.tasks.usecase.port.ProjectDTO;
import com.codurance.training.tasks.usecase.port.TaskDTO;
import com.codurance.training.tasks.usecase.port.ToDoListDTO;
import com.codurance.training.tasks.usecase.port.out.todolist.show.ShowPresenter;

public class ShowConsolePresenter implements ShowPresenter {

    private final PrintWriter out;

    public ShowConsolePresenter(PrintWriter out) {
        this.out = out;
    }

    public void present(ToDoListDTO toDoListDTO) {
        StringBuilder sb = new StringBuilder();
        for (ProjectDTO project : toDoListDTO.projectDTOs) {

            sb.append(project.projectName);
            sb.append("\r\n");
            for (TaskDTO task : project.taskDTOs) {
                sb.append(String.format("    [%c] %s: %s%n", (task.done ? 'x' : ' '), task.taskId,
                        task.description));
            }
            sb.append("\r\n");
        }

        out.print(sb.toString());
    }
}
