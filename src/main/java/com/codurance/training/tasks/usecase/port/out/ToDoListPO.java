package com.codurance.training.tasks.usecase.port.out;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.codurance.training.tasks.entity.Project;

/**
 * ToDoListPo
 */
public class ToDoListPO {

    private String id;
    private String lastTaskId;
    private Set<ProjectPO> projects;

    public ToDoListPO() {
        this.projects = new HashSet<>();
    }

    public ToDoListPO(String id, String lastTaskId) {
        this();
        this.id = id;
        this.lastTaskId = lastTaskId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastTaskId() {
        return lastTaskId;
    }

    public void setLastTaskId(String lastTaskId) {
        this.lastTaskId = lastTaskId;
    }

    public Set<ProjectPO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectPO> projects) {
        this.projects = new HashSet<>(projects);
    }
    
}
