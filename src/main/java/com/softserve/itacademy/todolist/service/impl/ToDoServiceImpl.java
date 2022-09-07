package com.softserve.itacademy.todolist.service.impl;

import com.softserve.itacademy.todolist.exception.NullEntityReferenceException;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.repository.ToDoRepository;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository todoRepository;
    private final UserService userService;

    @Override
    public ToDo create(ToDo todo) {
        if (todo != null) {
            return todoRepository.save(todo);
        }
        throw new NullEntityReferenceException("ToDo cannot be 'null'");
    }

    @Override
    public ToDo readById(long id) {
        return todoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("ToDo with id " + id + " not found"));
    }

    @Override
    public ToDo update(ToDo todo) {
        if (todo != null) {
            readById(todo.getId());
            return todoRepository.save(todo);
        }
        throw new NullEntityReferenceException("ToDo cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        ToDo todo = readById(id);
        todoRepository.delete(todo);
    }

    @Override
    public List<ToDo> getAll() {
        return todoRepository.findAll();
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
        return todoRepository.getByUserId(userId);


    }

    @Transactional
    @Override
    public void addCollaborator(ToDo toDo, User collaborator) {
        if (toDo.getOwner().equals(collaborator)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is owner, cannot be a collaborator");
        }
        if (toDo.getCollaborators().contains(collaborator)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already a collaborator");
        }
        toDo.getCollaborators().add(collaborator);
    }

    @Transactional
    @Override
    public void removeCollaborator(ToDo toDo, User collaborator) {
        if (!toDo.getCollaborators().contains(collaborator)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User is not a collaborator");
        }
        toDo.getCollaborators().remove(collaborator);
    }

    @Override
    public boolean isUserCollaborator(long todoId, long collaboratorId) {
        User user = userService.readById(collaboratorId);
        return readById(todoId).getCollaborators().contains(user);
    }
}
