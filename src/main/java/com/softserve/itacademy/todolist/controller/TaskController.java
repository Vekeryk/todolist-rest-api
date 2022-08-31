package com.softserve.itacademy.todolist.controller;


import com.softserve.itacademy.todolist.dto.task.TaskCreateRequest;
import com.softserve.itacademy.todolist.dto.task.TaskResponse;
import com.softserve.itacademy.todolist.dto.task.TaskUpdateRequest;
import com.softserve.itacademy.todolist.mappers.TaskMapper;
import com.softserve.itacademy.todolist.model.Task;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.service.TaskService;
import com.softserve.itacademy.todolist.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{u_id}/todos/{t_id}/tasks")
public class TaskController {

    private final TaskService taskService;

    private final ToDoService toDoService;

    private final TaskMapper taskMapper;

    @GetMapping
    List<TaskResponse> getAll() {return taskMapper.toTaskResponseList(taskService.getAll());}

    @GetMapping("/{id}")
    TaskResponse read(@PathVariable long id, @PathVariable("u_id") long userId, @PathVariable("t_id") long toDoId) {
        ToDo todo = toDoService.readById(id);
        if (todo.getOwner().getId() != userId){
            throw new EntityNotFoundException();
        }
        Task task = taskService.readById(id);
        if (task.getTodo().getId() != toDoId){
            throw new EntityNotFoundException();
        }
        return taskMapper.toTaskResponse(taskService.readById(id));
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody @Valid TaskCreateRequest taskCreateRequest) {
        Task task = taskService.create(taskMapper.toTaskFromCreate(taskCreateRequest));

        return ResponseEntity
                .created(URI.create(String.format("/api/users/{u_id}/todos/{t_id}/tasks/{%d}", task.getId())))
                .body(taskMapper.toTaskResponse(task));
    }

    @PatchMapping("/{id}")
    TaskResponse patch(@PathVariable long id, @RequestBody @Valid TaskUpdateRequest taskUpdateRequest) {
        Task task = taskService.readById(id);
        taskMapper.patchTaskModel(task, taskUpdateRequest);

        return taskMapper.toTaskResponse(taskService.update(task));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
