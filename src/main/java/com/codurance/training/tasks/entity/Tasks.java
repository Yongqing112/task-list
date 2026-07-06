package com.codurance.training.tasks.entity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;

public class Tasks {
    private final Map<ProjectName, List<Task>> tasks;

    public Tasks() {
        this.tasks = new LinkedHashMap<>();
    }

    public Map<ProjectName, List<Task>> getTasks() {
        return tasks;
    }

    public Set<Map.Entry<ProjectName, List<Task>>> entrySet() {
        return tasks.entrySet();
    }

    public void put(ProjectName projectName, List<Task> taskList) {
        tasks.put(projectName, taskList);
    }

    public List<Task> get(ProjectName projectName) {
        return tasks.get(projectName);
    }

}
