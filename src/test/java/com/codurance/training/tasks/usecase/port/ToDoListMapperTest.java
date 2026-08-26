package com.codurance.training.tasks.usecase.port;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.codurance.training.tasks.entity.ProjectName;
import com.codurance.training.tasks.entity.ToDoList;
import com.codurance.training.tasks.entity.ToDoListId;
import com.codurance.training.tasks.usecase.port.out.ProjectPO;
import com.codurance.training.tasks.usecase.port.out.TaskPO;
import com.codurance.training.tasks.usecase.port.out.ToDoListPO;

public class ToDoListMapperTest {

    @Test
    public void toDto() {
        ToDoListId toDoListId = new ToDoListId("123456");
        ToDoList toDoList = new ToDoList(toDoListId, 5);
        ProjectName projectName = new ProjectName("Test");
        toDoList.addProject(projectName);
        toDoList.addProject(ProjectName.of("p2"));
        toDoList.addTask(projectName, "Read DDD", false);
        toDoList.addTask(projectName, "Read CA", false);
        toDoList.addTask(projectName, "Read Pattern", true);

        ToDoListDTO toDoListDto = ToDoListMapper.toDTO(toDoList);

        assertEquals(toDoList.geToDoListId().value(), toDoListDto.toDoListId);
        assertEquals(toDoList.getProjects().size(), toDoListDto.projectDTOs.size());
        for(int j = 0; j < toDoList.getProjects().size(); j ++){
            for(int i = 0; i< toDoList.getProjects().get(j).getTasks().size(); i++){
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).getId().value(), toDoListDto.projectDTOs.get(j).taskDTOs.get(i).taskId);
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).getDescription(), toDoListDto.projectDTOs.get(j).taskDTOs.get(i).description);
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).isDone(), toDoListDto.projectDTOs.get(j).taskDTOs.get(i).done);
            }
        }
    }


    @Test
    public void toDomain() {
        ToDoListPO toDoListPo = new ToDoListPO();
        toDoListPo.setId("testId");
        toDoListPo.setLastTaskId(1L);

        ProjectPO projectPo = new ProjectPO();
        String name = "Test Project";
        int order = 0;
        projectPo.setProjectName(name);
        projectPo.setOrder(order);
        List<TaskPO> taskPos = new ArrayList<>();
        taskPos.add(new TaskPO("1", "Study Refactoring", false));
        projectPo.setTaskPOs(taskPos);
        List<ProjectPO> projectPos = new ArrayList<>();
        projectPos.add(projectPo);
        toDoListPo.setProjectPOs(projectPos);


        ToDoList toDoList = ToDoListMapper.toDomain(toDoListPo);

        assertEquals("testId", toDoList.geToDoListId().value());
        assertEquals(1L, toDoList.getLastTaskId());
        assertEquals(toDoList.getProjects().size(), toDoListPo.getProjectPOs().size());

        for(int j = 0; j < toDoList.getProjects().size(); j ++){
            for(int i = 0; i< toDoList.getProjects().get(j).getTasks().size(); i++){
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).getId().value(), toDoListPo.getProjectPOs().get(j).getTaskPOs().get(i).getTaskId());
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).getDescription(), toDoListPo.getProjectPOs().get(j).getTaskPOs().get(i).getDescription());
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).isDone(), toDoListPo.getProjectPOs().get(j).getTaskPOs().get(i).getDone());
            }
        }
    }


    @Test
    public void toPo() {
        ToDoListId toDoListId = new ToDoListId("123456");
        ToDoList toDoList = new ToDoList(toDoListId, 5);
        ProjectName projectName = new ProjectName("Test");
        toDoList.addProject(projectName);
        toDoList.addProject(ProjectName.of("p2"));
        toDoList.addTask(projectName, "Read DDD", false);
        toDoList.addTask(projectName, "Read CA", false);
        toDoList.addTask(projectName, "Read Pattern", true);

        ToDoListPO toDoListPo = ToDoListMapper.toPO(toDoList);

        assertEquals(toDoList.geToDoListId().value(), toDoListPo.getId());
        assertEquals(toDoList.getProjects().size(), toDoListPo.getProjectPOs().size());
        for(int j = 0; j < toDoList.getProjects().size(); j++){
            for(int i = 0; i< toDoList.getProjects().get(j).getTasks().size(); i++){
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).getId().value(), toDoListPo.getProjectPOs().get(j).getTaskPOs().get(i).getTaskId());
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).getDescription(), toDoListPo.getProjectPOs().get(j).getTaskPOs().get(i).getDescription());
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).isDone(), toDoListPo.getProjectPOs().get(j).getTaskPOs().get(i).getDone());
            }
        }
    }
}