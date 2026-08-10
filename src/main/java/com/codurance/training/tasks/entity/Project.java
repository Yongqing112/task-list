package com.codurance.training.tasks.entity;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private ProjectName projectName;

    private List<Task> tasks;

    public Project(ProjectName projectName){
        this.projectName = projectName;
        this.tasks = new ArrayList<>();
    }

    public Project(ProjectName projectName, List<Task> tasks){
        this(projectName);
        this.tasks.addAll(tasks);
    }

    public void setProjectName(ProjectName projectName){
        this.projectName = projectName;
    }

    public ProjectName getProjectName(){
        return projectName;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public List<Task> getTasks(){
        return tasks;
    }

    public void setTaskDone(TaskId id, boolean done) {
        tasks.stream().filter(t -> t.getId().equals(id)).findFirst().ifPresent(t -> t.setDone(done));
    }

    public boolean containTask(TaskId id) {
        return this.tasks.stream().anyMatch(t -> t.getId().equals(id));
    }

}
