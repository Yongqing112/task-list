package com.codurance.training.tasks.entity;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private ProjectName projectName;

    private List<Task> tasks;

    public Project(){
        this.tasks = new ArrayList<>();
    }

    public Project(ProjectName projectName, List<Task> tasks){
        this();
        this.projectName = projectName;
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

}
