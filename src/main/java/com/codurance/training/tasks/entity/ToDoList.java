package com.codurance.training.tasks.entity;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class ToDoList {

    private final ToDoListId toDoListId;
    private final List<Project> projects;
    private long lastTaskId;

    public ToDoList(ToDoListId toDoListId) {
        this(toDoListId, 0);
    }

    public ToDoList(ToDoListId toDoListId, long lastTaskId) {
        this.toDoListId = toDoListId;
        this.lastTaskId = lastTaskId;
        this.projects = new ArrayList<>();
    }

    public ToDoList(ToDoListId toDoListId, long lastTaskId, List<Project> projects) {
        this(toDoListId, lastTaskId);
        this.projects.addAll(projects);
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

    public long getLastTaskId() {
        return lastTaskId;
    }

    private long nextId() {
        return ++lastTaskId;
    }

    public boolean containTask(TaskId id) {
        return projects.stream().anyMatch(project -> project.containTask(id));
    }
}
