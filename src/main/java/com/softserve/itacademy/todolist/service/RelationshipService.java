package com.softserve.itacademy.todolist.service;

public interface RelationshipService {
    void checkToDoForUser(long toDoId, long userId);

    void checkTaskForToDo(long taskId, long toDoId);

    void checkTaskForToDoAndUser(long taskId, long toDoId, long userId);
}
