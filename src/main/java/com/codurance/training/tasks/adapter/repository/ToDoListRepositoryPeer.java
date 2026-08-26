
package com.codurance.training.tasks.adapter.repository;

import java.util.Optional;

import com.codurance.training.tasks.usecase.port.out.ToDoListPO;

public interface ToDoListRepositoryPeer {

    void save(ToDoListPO toDoListPo);

    Optional<ToDoListPO> findById(String id);

    void delete(ToDoListPO toDoListPo);
}