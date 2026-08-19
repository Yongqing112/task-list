
package com.codurance.training.tasks.usecase.port.out;

import java.util.Optional;

public interface ToDoListPoRepository {

    void save(ToDoListPO toDoListPo);

    Optional<ToDoListPO> findById(String id);

    void delete(ToDoListPO toDoListPo);
}