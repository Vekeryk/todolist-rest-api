package com.softserve.itacademy.todolist.service.impl;

import com.softserve.itacademy.todolist.service.RelationshipService;
import com.softserve.itacademy.todolist.service.TaskService;
import com.softserve.itacademy.todolist.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class RelationshipServiceImpl implements RelationshipService {
    private final ToDoService toDoService;
    private final TaskService taskService;

    public void checkToDoForUser(long toDoId, long userId) {
        if (toDoService.readById(toDoId).getOwner().getId() != userId) {
            throw new EntityNotFoundException(
                    "ToDo with id " + toDoId + " not found for user with id " + userId
            );
        }
    }

    public void checkTaskForToDo(long taskId, long toDoId) {
        if (taskService.readById(taskId).getTodo().getId() != toDoId) {
            throw new EntityNotFoundException(
                    "Task with id " + taskId + " not found for ToDo with id " + toDoId
            );
        }
    }

    public void checkTaskForToDoAndUser(long taskId, long toDoId, long userId) {
        checkTaskForToDo(taskId, toDoId);
        checkToDoForUser(toDoId, userId);
    }
}
