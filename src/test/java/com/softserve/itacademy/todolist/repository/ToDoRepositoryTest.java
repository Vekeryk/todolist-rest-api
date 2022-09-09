package com.softserve.itacademy.todolist.repository;

import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
class ToDoRepositoryTest {

    @Autowired
    private ToDoRepository toDoRepository;
    @Autowired
    private TestEntityManager entityManager;
    private User user;
    private User collaborator;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("doe@mail.com");
        user.setPassword("password");
        entityManager.persist(user);

        collaborator = new User();
        collaborator.setFirstName("Walter");
        collaborator.setLastName("White");
        collaborator.setEmail("white@mail.com");
        collaborator.setPassword("password");
        entityManager.persist(collaborator);
    }

    @Test
    void shouldGetAllUserToDosIncludeCollaborateOnes() {
        ToDo toDo = new ToDo();
        toDo.setTitle("User ToDo");
        toDo.setOwner(user);
        entityManager.persist(toDo);

        ToDo toDo2 = new ToDo();
        toDo2.setTitle("Collaborator ToDo");
        toDo2.setOwner(collaborator);
        entityManager.persist(toDo2);

        toDo.getCollaborators().add(collaborator);
        List<ToDo> expected = List.of(toDo, toDo2);

        // when
        List<ToDo> actual = toDoRepository.getAllToDosOfUser(collaborator.getId());

        Assertions.assertEquals(actual, expected);
    }
}