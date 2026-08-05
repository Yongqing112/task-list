package com.codurance.training.tasks.entity;

import java.util.List;
import java.util.ArrayList;

public class ToDoList {
    private final List<Project> projects;

    public ToDoList() {
        this.projects = new ArrayList<>();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(ProjectName projectName, List<Task> taskList) {
        projects.add(new Project(projectName, taskList));
    }

    public List<Task> getTasks(ProjectName projectName) {
        return projects.stream()
        .filter(project -> project.getProjectName().equals(projectName))
        .findFirst()
        .map(Project::getTasks)
        .orElse(null);
    }

}
