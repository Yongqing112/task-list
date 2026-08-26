package com.codurance.training.tasks.usecase.port.out;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * ToDoListPo
 */
@Entity
@Table(name = "toDoList")
public class ToDoListPO {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "last_task_id")
    private Long lastTaskId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "id_fk")
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
