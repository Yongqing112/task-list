package com.codurance.training.tasks.usecase.port;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.codurance.training.tasks.entity.Project;
import com.codurance.training.tasks.entity.ProjectName;
import com.codurance.training.tasks.entity.Task;
import com.codurance.training.tasks.entity.TaskId;
import com.codurance.training.tasks.usecase.port.out.ProjectPO;
import com.codurance.training.tasks.usecase.port.out.TaskPO;

public class ProjectMapperTest {

    @Test
    void toDto() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(TaskId.of("Task 1"), "Study DDD", true));
        tasks.add(new Task(TaskId.of("Task 2"), "Study CA", false));
        Project project = new Project(ProjectName.of("Project 1"), tasks);

        ProjectDTO projectDto = ProjectMapper.toDTO(project);

        assertEquals(project.getProjectName().value(), projectDto.projectName);
        assertEquals(project.getTasks().size(), projectDto.taskDTOs.size());
        for (int i = 0; i < project.getTasks().size(); i++) {
            assertEquals(project.getTasks().get(i).getId().value(), projectDto.taskDTOs.get(i).taskId);
            assertEquals(project.getTasks().get(i).getDescription(), projectDto.taskDTOs.get(i).description);
            assertEquals(project.getTasks().get(i).isDone(), projectDto.taskDTOs.get(i).done);
        }
    }

    @Test
    public void toDomain() {
        ProjectPO projectPo = new ProjectPO();
        String name = "Test Project";
        int order = 0;
        projectPo.setProjectName(name);
        projectPo.setOrder(order);
        List<TaskPO> taskPos = new ArrayList<>();
        taskPos.add(new TaskPO("1", "Study Refactoring", false));
        projectPo.setTaskPOs(taskPos);

        Project project = ProjectMapper.toDomain(projectPo);

        assertNotNull(project);
        assertEquals(projectPo.getProjectName(), project.getProjectName().value());
        assertEquals(projectPo.getTaskPOs().size(), project.getTasks().size());
        for (int i = 0; i < project.getTasks().size(); i++) {
            assertEquals(project.getTasks().get(i).getId().value(), projectPo.getTaskPOs().get(i).getTaskId());
            assertEquals(project.getTasks().get(i).getDescription(), projectPo.getTaskPOs().get(i).getDescription());
            assertEquals(project.getTasks().get(i).isDone(), projectPo.getTaskPOs().get(i).getDone());
        }
    }

    @Test
    void toPo() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(TaskId.of("Task 1"), "Study DDD", true));
        tasks.add(new Task(TaskId.of("Task 2"), "Study CA", false));
        Project project = new Project(ProjectName.of("My Project"), tasks);

        ProjectPO projectPo = ProjectMapper.toPO(project, 0);

        assertEquals(project.getProjectName().value(), projectPo.getProjectName());

        assertEquals(0, projectPo.getOrder());
        assertEquals(projectPo.getTaskPOs().size(), project.getTasks().size());
        for (int i = 0; i < project.getTasks().size(); i++) {
            assertEquals(project.getTasks().get(i).getId().value(), projectPo.getTaskPOs().get(i).getTaskId());
            assertEquals(project.getTasks().get(i).getDescription(), projectPo.getTaskPOs().get(i).getDescription());
            assertEquals(project.getTasks().get(i).isDone(), projectPo.getTaskPOs().get(i).getDone());
        }
    }
}
