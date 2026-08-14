package com.codurance.training.tasks.usecase.port;

import java.util.List;

import com.codurance.training.tasks.entity.Project;

public class ProjectMapper {

    public static List<ProjectDTO> toDTO(List<Project> projects) {
        return projects.stream().map(ProjectMapper::toDTO).toList();
    }

    public static ProjectDTO toDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.projectName = project.getProjectName().value();
        projectDTO.taskDTOs = TaskMapper.toDTO(project.getTasks());

        return projectDTO;
    }
}
