package com.codurance.training.tasks.usecase.port.out;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ProjectPO
 */
public class ProjectPO implements Comparable<ProjectPO>{

    private String projectName;
    private int order;
    private Set<TaskPO> tasks;


    public ProjectPO() {
        this.tasks = new HashSet<>();
    }

    public ProjectPO(String projectName, int order) {
        this();
        this.order = order;
        this.projectName = projectName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Set<TaskPO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskPO> tasks) {
        this.tasks = new HashSet<>(tasks);
    }

    @Override
    public int compareTo(ProjectPO that) {
        return this.getOrder() - that.getOrder();
    }

    
}
