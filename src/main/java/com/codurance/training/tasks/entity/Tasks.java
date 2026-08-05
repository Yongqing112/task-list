package com.codurance.training.tasks.entity;

import java.util.List;
import java.util.ArrayList;

public class Tasks {
    private final List<Project> projects;

    public Tasks() {
        this.projects = new ArrayList<>();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<Project> entrySet() {
        return projects;
    }

    public void put(ProjectName projectName, List<Task> taskList) {
        projects.add(new Project(projectName, taskList));
    }

    public List<Task> get(ProjectName projectName) {
        return projects.stream()
        .filter(project -> project.getProjectName().equals(projectName))
        .findFirst()
        .map(Project::getTasks)
        .orElse(null);
    }

}
