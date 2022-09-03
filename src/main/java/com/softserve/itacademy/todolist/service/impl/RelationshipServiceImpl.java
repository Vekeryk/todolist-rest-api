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

    public boolean isRelatedToDoForUser(long toDoId, long userId) {
        if (toDoService.readById(toDoId).getOwner().getId() != userId) {
            throw new EntityNotFoundException(
                    "ToDo with id " + toDoId + " not found for user with id " + userId
            );
        }
        return true;
    }

    public boolean isRelatedTaskForToDo(long taskId, long toDoId) {
        if (taskService.readById(taskId).getTodo().getId() != toDoId) {
            throw new EntityNotFoundException(
                    "Task with id " + taskId + " not found for ToDo with id " + toDoId
            );
        }
        return true;
    }

    public boolean isRelatedTaskForToDoAndUser(long taskId, long toDoId, long userId) {
        isRelatedTaskForToDo(taskId, toDoId);
        isRelatedToDoForUser(toDoId, userId);
        return true;
    }
}
