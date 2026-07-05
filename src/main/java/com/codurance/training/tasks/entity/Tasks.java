package com.codurance.training.tasks.entity;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashMap;

public class Tasks {
    private final Map<String, List<Task>> tasks;

    public Tasks() {
        this.tasks = new LinkedHashMap<>();
    }

    public Map<String, List<Task>> getTasks() {
        return tasks;
    }

    public Set<Map.Entry<String, List<Task>>> entrySet() {
        return tasks.entrySet();
    }

    public void put(String projectName, List<Task> taskList) {
        tasks.put(projectName, taskList);
    }

    public List<Task> get(String projectName) {
        return tasks.get(projectName);
    }

}
