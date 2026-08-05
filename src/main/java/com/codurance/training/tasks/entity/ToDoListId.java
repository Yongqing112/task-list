package com.codurance.training.tasks.entity;

public record ToDoListId(String value) {
    public static ToDoListId of(String id){
        return new ToDoListId(id);
    }
}
