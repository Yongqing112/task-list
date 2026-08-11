package com.codurance.training.tasks.entity;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class ToDoList {

    private final ToDoListId toDoListId;
    private final List<Project> projects;
    private long lastId;

    public ToDoList(ToDoListId toDoListId) {
        this.toDoListId = toDoListId;
        this.projects = new ArrayList<>();
        this.lastId = 0;
    }

    public ToDoListId geToDoListId() {
        return toDoListId;
    }

    public void addProject(ProjectName projectName) {
        if(containProject(projectName)) {
            return;
        }
        projects.add(new Project(projectName));
    }

    public Optional<Project> getProject(ProjectName projectName) {
        return projects.stream().filter(project -> project.getProjectName().equals(projectName)).findFirst();
    }

    public List<Project> getProjects() {
        return projects.stream().map(project -> (Project) new ReadOnlyProject(project)).toList();
    }

    public void addTask(ProjectName projectName, String description, boolean done) {
        Optional<Project> project = this.getProject(projectName);
        TaskId taskId = TaskId.of(nextId());
        project.ifPresent(p -> p.addTask(new Task(taskId, description, false)));
    }

    public List<Task> getTasks(ProjectName projectName) {
        return projects.stream()
                .filter(project -> project.getProjectName().equals(projectName))
                .findFirst()
                .map(project -> project.getTasks().stream().map(task -> (Task) new ReadOnlyTask(task)).toList())
                .orElse(null);
    }

    public void setDone(TaskId id, boolean done) {
        this.projects.stream().filter(p -> p.containTask(id)).findFirst().ifPresent(p -> p.setTaskDone(id, done));
    }

    private boolean containProject(ProjectName projectName) {
        return projects.stream().anyMatch(p -> p.getProjectName().equals(projectName));
    }

    private long nextId() {
        return ++lastId;
    }

    public boolean containTask(TaskId id) {
        return projects.stream().anyMatch(project -> project.containTask(id));
    }
}
