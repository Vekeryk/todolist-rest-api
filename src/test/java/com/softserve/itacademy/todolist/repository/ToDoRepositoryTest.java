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
class ToDoRepositoryTest extends RepositoryTest {

    @Autowired
    private ToDoRepository toDoRepository;
    @Autowired
    private TestEntityManager entityManager;
    private User user;
    private User collaborator;

    @BeforeEach
    void setUp() {
        user = createUser("John", "Doe", "doe@mail.com", "password");
        entityManager.persist(user);

        collaborator = createUser("Walter", "White", "white@mail.com", "password");
        entityManager.persist(collaborator);
    }

    @Test
    void shouldGetAllUserToDosIncludeCollaborateOnes() {
        ToDo toDo = createToDo("User ToDo", user);
        entityManager.persist(toDo);
        ToDo toDo2 = createToDo("Collaborator ToDo", collaborator);
        entityManager.persist(toDo2);

        toDo.getCollaborators().add(collaborator);
        List<ToDo> expected = List.of(toDo, toDo2);
        // when
        List<ToDo> actual = toDoRepository.getAllToDosOfUser(collaborator.getId());

        Assertions.assertEquals(actual, expected);
    }
}