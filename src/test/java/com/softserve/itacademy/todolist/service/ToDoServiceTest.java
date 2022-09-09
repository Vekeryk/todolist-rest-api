package com.softserve.itacademy.todolist.service;

import com.softserve.itacademy.todolist.exception.NullEntityReferenceException;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.repository.ToDoRepository;
import com.softserve.itacademy.todolist.service.impl.ToDoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToDoServiceTest {

    @Mock
    private ToDoRepository toDoRepository;
    @Mock
    private UserService userService;
    private ToDoService underTest;
    private ToDo todo;

    @BeforeEach
    void setUp() {
        underTest = new ToDoServiceImpl(toDoRepository, userService);
        todo = new ToDo();
        todo.setId(1L);
        todo.setTitle("Title");
        todo.setOwner(new User());
    }

    @Test
    void shouldCreate() {
        underTest.create(todo);

        verify(toDoRepository).save(todo);
    }

    @Test
    void shouldThrowOnCreateNull() {
        assertThrows(NullEntityReferenceException.class, () -> underTest.create(null));
    }

    @Test
    void shouldReadById() {
        when(toDoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));
        underTest.readById(todo.getId());

        verify(toDoRepository).findById(todo.getId());
    }

    @Test
    void shouldThrowOnReadByNonExistentId() {
        assertThrows(EntityNotFoundException.class, () -> underTest.readById(-1));
    }

    @Test
    void shouldUpdate() {
        when(toDoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));
        underTest.update(todo);

        verify(toDoRepository).save(todo);
    }

    @Test
    void shouldThrowOnUpdateNull() {
        assertThrows(NullEntityReferenceException.class, () -> underTest.update(null));
    }

    @Test
    void shouldDelete() {
        when(toDoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));
        underTest.delete(todo.getId());

        verify(toDoRepository).delete(todo);
    }

    @Test
    void shouldThrowOnDeleteByNonExistentId() {
        assertThrows(EntityNotFoundException.class, () -> underTest.delete(-1));
    }

    @Test
    void shouldGetAll() {
        underTest.getAll();

        verify(toDoRepository).findAll();
    }

    @Test
    void shouldGetByUserId() {
        long id = 1;

        underTest.getByUserId(id);

        verify(toDoRepository).getAllToDosOfUser(id);
    }

    @Test
    void shouldAddCollaborator() {
        User collaborator = new User();

        underTest.addCollaborator(todo, collaborator);

        List<User> expected = List.of(collaborator);
        assertEquals(expected, todo.getCollaborators());
    }

    @Test
    void shouldThrowOnAddOwnerAsCollaborator() {
        assertThrows(ResponseStatusException.class, () -> underTest.addCollaborator(todo, todo.getOwner()));
    }

    @Test
    void shouldThrowOnAddPresentCollaborator() {
        User collaborator = new User();

        underTest.addCollaborator(todo, collaborator);

        assertThrows(ResponseStatusException.class, () -> underTest.addCollaborator(todo, collaborator));
    }

    @Test
    void shouldRemoveCollaborator() {
        User collaborator = new User();
        todo.getCollaborators().add(collaborator);

        underTest.removeCollaborator(todo, collaborator);

        List<User> expected = List.of();
        assertEquals(expected, todo.getCollaborators());
    }

    @Test
    void shouldThrowOnRemoveAbsentCollaborator() {
        User collaborator = new User();

        assertThrows(ResponseStatusException.class, () -> underTest.removeCollaborator(todo, collaborator));
    }

    @Test
    void shouldReturnTrueOnIsUserCollaborator() {
        User collaborator = new User();
        collaborator.setId(1L);
        todo.getCollaborators().add(collaborator);

        when(userService.readById(collaborator.getId())).thenReturn(collaborator);
        when(toDoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));
        boolean actual = underTest.isUserCollaborator(todo.getId(), collaborator.getId());

        assertTrue(actual);
    }

    @Test
    void shouldReturnFalseOnIsUserCollaborator() {
        User collaborator = new User();
        collaborator.setId(1L);

        when(userService.readById(collaborator.getId())).thenReturn(collaborator);
        when(toDoRepository.findById(todo.getId())).thenReturn(Optional.of(todo));
        boolean actual = underTest.isUserCollaborator(todo.getId(), collaborator.getId());

        assertFalse(actual);
    }
}