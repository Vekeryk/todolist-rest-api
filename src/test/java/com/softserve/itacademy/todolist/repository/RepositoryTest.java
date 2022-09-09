package com.softserve.itacademy.todolist.repository;

import com.softserve.itacademy.todolist.model.*;

public class RepositoryTest {
    User createUser(String firstName, String lastName, String email, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    ToDo createToDo(String title, User owner) {
        ToDo toDo = new ToDo();
        toDo.setTitle(title);
        toDo.setOwner(owner);
        return toDo;
    }

    Task createTask(String name, ToDo toDo) {
        Task task = new Task();
        task.setName(name);
        task.setTodo(toDo);
        task.setState(State.NEW);
        task.setPriority(Priority.LOW);
        return task;
    }
}
