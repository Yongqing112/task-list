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
            sb.append(System.lineSeparator());
            for (TaskDTO task : project.taskDTOs) {

                if (null != task.deadline) {
                    sb.append(String.format("    [%c] %s: %s %s%n", (task.done ? 'x' : ' '), task.taskId,
                            task.description, task.deadline.toLocalDate().toString()));
                } else {
                    sb.append(
                            String.format("    [%c] %s: %s%n", (task.done ? 'x' : ' '), task.taskId, task.description));
                }

            }
            sb.append(System.lineSeparator());
        }

        out.print(sb.toString());
    }
}
