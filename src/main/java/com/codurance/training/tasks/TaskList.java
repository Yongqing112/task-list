package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.codurance.training.tasks.adapter.repository.ToDoListInMemoryRepository;
import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.Execute;
import com.codurance.training.tasks.usecase.port.out.ToDoListRepository;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final ToDoList toDoList = new ToDoList(ToDoListId.of(DEFAULT_TO_DO_LIST_ID));
    private final BufferedReader in;
    private final PrintWriter out;
    private final ToDoListRepository repository;

    public static final String DEFAULT_TO_DO_LIST_ID = "001";

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
        repository = new ToDoListInMemoryRepository();
        if (repository.findById(ToDoListId.of(DEFAULT_TO_DO_LIST_ID)).isEmpty()) {
            repository.save(toDoList);
        }
    }

    public void run() {
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            new Execute(out, repository).execute(command);
        }
    }

}
