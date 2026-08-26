package com.codurance.training.tasks.usecase.port.out;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectPO
 */
public class ProjectPO implements Comparable<ProjectPO>{

    private String projectName;
    private int order;
    private List<TaskPO> taskPOs;


    public ProjectPO() {
        this.taskPOs = new ArrayList<>();
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

    public List<TaskPO> getTaskPOs() {
        return new ArrayList<>(taskPOs);
    }

    public void setTaskPOs(List<TaskPO> tasks) {
        this.taskPOs = new ArrayList<>(tasks);
    }

    @Override
    public int compareTo(ProjectPO that) {
        return this.getOrder() - that.getOrder();
    }

    
}
