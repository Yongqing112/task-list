package com.codurance.training.tasks.adapter.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.codurance.training.tasks.usecase.port.out.ToDoListPO;

@Component
public class ToDoListInMemoryRepositoryPeer implements ToDoListRepositoryPeer {

    private final List<ToDoListPO> store;

    public ToDoListInMemoryRepositoryPeer() {
        this.store = new ArrayList<>();
    }

    @Override
    public void delete(ToDoListPO toDoListPo) {
        this.store.removeIf(x -> x.getId().equals(toDoListPo.getId()));
    }

    @Override
    public Optional<ToDoListPO> findById(String id) {
        return store.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    @Override
    public void save(ToDoListPO toDoListPo) {
        this.store.removeIf(x -> x.getId().equals(toDoListPo.getId()));
        this.store.add(toDoListPo);
    }

}
