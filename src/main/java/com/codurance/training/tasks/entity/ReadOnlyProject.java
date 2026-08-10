package com.codurance.training.tasks.entity;

import java.util.List;

public class ReadOnlyProject extends Project {

    private Project project;

    ReadOnlyProject(Project project) {
        super(project.getProjectName(), project.getTasks());
        this.project = project;
    }

    @Override
    public void setProjectName(ProjectName projectName) {
        throw new UnsupportedOperationException("Read Only");
    }

    @Override
    public void addTask(Task task) {
        throw new UnsupportedOperationException("Read Only");
    }

    @Override
    public List<Task> getTasks() {
        return project.getTasks().stream().map(task -> (Task) new ReadOnlyTask(task)).toList();
    }
}
