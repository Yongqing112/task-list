package com.codurance.training.tasks.adapter.repository;

import org.springframework.data.repository.CrudRepository;

import com.codurance.training.tasks.usecase.port.out.ToDoListPO;

/**
 * ToDoListCrudRepositoryPeer
 */
public interface ToDoListCrudRepositoryPeer extends CrudRepository<ToDoListPO, String> {
}
