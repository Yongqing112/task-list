package com.codurance.training.tasks.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ToDoListTest {

    @Test
    public void add_a_project_with_duplicate_name_has_no_effect() {
        ToDoList toDoList = new ToDoList(ToDoListId.of("001"));

        toDoList.addProject(ProjectName.of("p1"));
        assertEquals(1, toDoList.getProjects().size());

        toDoList.addProject(ProjectName.of("p1"));
        assertEquals(1, toDoList.getProjects().size());

    }

}
