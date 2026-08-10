package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.entity.Project;
import com.codurance.training.tasks.entity.Task;
import com.codurance.training.tasks.entity.TaskId;
import com.codurance.training.tasks.usecase.Add;
import com.codurance.training.tasks.usecase.Help;
import com.codurance.training.tasks.usecase.SetDone;
import com.codurance.training.tasks.usecase.Show;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final ToDoListId DEFAULT_TO_DO_LIST_ID = ToDoListId.of("001");
    private final ToDoList toDoList = new ToDoList(DEFAULT_TO_DO_LIST_ID);
    private final BufferedReader in;
    private final PrintWriter out;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
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
            execute(command);
        }
    }

    private void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                new Show(toDoList, out).show();;
                break;
            case "add":
                new Add(toDoList, out).add(commandRest[1]);
                break;
            case "check":
                check(commandRest[1]);
                break;
            case "uncheck":
                uncheck(commandRest[1]);
                break;
            case "help":
                new Help(out).help();
                break;
            default:
                error(command);
                break;
        }
    }

    private void check(String idString) {
        new SetDone(toDoList, out).setDone(idString, true);
    }

    private void uncheck(String idString) {
        new SetDone(toDoList, out).setDone(idString, false);
    }

    private void help() {
        out.println("Commands:");
        out.println("  show");
        out.println("  add project <project name>");
        out.println("  add task <project name> <task description>");
        out.println("  check <task ID>");
        out.println("  uncheck <task ID>");
        out.println();
    }

    private void error(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }

}
