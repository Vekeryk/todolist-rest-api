package com.softserve.itacademy.todolist.repository;

import com.softserve.itacademy.todolist.model.Task;
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
class TaskRepositoryTest extends RepositoryTest {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TestEntityManager entityManager;
    private ToDo toDo;
    private ToDo anotherToDo;

    @BeforeEach
    void setUp() {
        User john = createUser("John", "Doe", "doe@mail.com", "password");
        entityManager.persist(john);
        User walter = createUser("Walter", "White", "white@mail.com", "123456");
        entityManager.persist(walter);

        toDo = entityManager.persist(createToDo("John ToDo", john));
        anotherToDo = entityManager.persist(createToDo("Walter ToDo", walter));
    }

    @Test
    void getByTodoId() {
        Task task1 = entityManager.persist(createTask("Tested Task 1", toDo));
        Task task2 = entityManager.persist(createTask("Tested Task 2", toDo));
        entityManager.persist(createTask("Another Task 1", anotherToDo));

        List<Task> expected = List.of(task1, task2);
        // when
        List<Task> actual = taskRepository.getByTodoId(toDo.getId());

        Assertions.assertEquals(actual, expected);
    }
}