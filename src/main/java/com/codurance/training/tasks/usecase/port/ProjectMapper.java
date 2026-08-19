package com.codurance.training.tasks.usecase.port;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.codurance.training.tasks.entity.Project;
import com.codurance.training.tasks.entity.ProjectName;
import com.codurance.training.tasks.usecase.port.out.ProjectPO;

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

    public static Project toDomain(ProjectPO projectPO) {
        return new Project(ProjectName.of(projectPO.getProjectName()), TaskMapper.toDomain(projectPO.getTaskPOs()));
    }

    public static List<Project> toDomain(List<ProjectPO> projectPOs) {
        return projectPOs.stream().map(ProjectMapper::toDomain).toList();
    }

    public static ProjectPO toPO(Project project, int order) {
        ProjectPO projectPo = new ProjectPO(project.getProjectName().value(), order);
        projectPo.setTaskPOs(TaskMapper.toPO(project.getTasks()));
        return projectPo;
    }

    public static List<ProjectPO> toPO(List<Project> projects) {

        List<ProjectPO> projectPos = new ArrayList<>();
        for (int i = 0; i < projects.size(); i++) {
            projectPos.add(toPO(projects.get(i), i));
        }
        Collections.sort(projectPos);
        return projectPos;
    }
}
