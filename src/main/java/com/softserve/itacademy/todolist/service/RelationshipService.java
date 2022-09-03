package com.softserve.itacademy.todolist.service;

public interface RelationshipService {
    boolean isRelatedToDoForUser(long toDoId, long userId);
    boolean isRelatedTaskForToDo(long taskId, long toDoId);
    boolean isRelatedTaskForToDoAndUser(long taskId, long toDoId, long userId);
}
