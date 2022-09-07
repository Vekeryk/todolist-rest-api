package com.softserve.itacademy.todolist.service;

import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;

import java.util.List;

public interface ToDoService {
    ToDo create(ToDo todo);
    ToDo readById(long id);
    ToDo update(ToDo todo);
    void delete(long id);
    List<ToDo> getAll();
    List<ToDo> getByUserId(long userId);
    void addCollaborator(ToDo toDo, User collaborator);
    void removeCollaborator(ToDo toDo, User collaborator);
    boolean isUserCollaborator(long todoId, long userId);
}
