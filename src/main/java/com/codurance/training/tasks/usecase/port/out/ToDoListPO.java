package com.codurance.training.tasks.usecase.port.out;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * ToDoListPo
 */
public class ToDoListPO {

    private String id;
    private Long lastTaskId;
    private Set<ProjectPO> projectPOs;

    public ToDoListPO() {
        this.projectPOs = new HashSet<>();
    }

    public ToDoListPO(String id, Long lastTaskId) {
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

    public Long getLastTaskId() {
        return lastTaskId;
    }

    public void setLastTaskId(Long lastTaskId) {
        this.lastTaskId = lastTaskId;
    }

    public List<ProjectPO> getProjectPOs() {
        return new ArrayList<>(projectPOs);
    }

    public void setProjectPOs(List<ProjectPO> projects) {
        this.projectPOs = new LinkedHashSet<>(projects);
    }
    
}
