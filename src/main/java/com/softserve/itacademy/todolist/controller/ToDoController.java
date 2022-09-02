package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.todo.ToDoRequest;
import com.softserve.itacademy.todolist.dto.todo.ToDoResponse;
import com.softserve.itacademy.todolist.mappers.ToDoMapper;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.service.RelationshipService;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{u_id}/todos")
public class ToDoController {

    private final ToDoService toDoService;
    private final UserService userService;
    private final RelationshipService relationshipService;
    private final ToDoMapper toDoMapper;

    @GetMapping
    List<ToDoResponse> getAll(@PathVariable("u_id") long userId) {
        return toDoMapper.toToDoResponseList(toDoService.getByUserId(userId));
    }

    @GetMapping("/{id}")
    ToDoResponse read(@PathVariable long id,
                      @PathVariable("u_id") long userId) {
        relationshipService.checkToDoForUser(id, userId);
        return toDoMapper.toToDoResponse(toDoService.readById(id));
    }

    @PostMapping
    ResponseEntity<?> create(@PathVariable("u_id") long userId,
                             @RequestBody @Valid ToDoRequest todoRequest) {
        ToDo toDo = toDoMapper.toToDo(todoRequest);
        toDo.setOwner(userService.readById(userId));
        ToDo createdToDo = toDoService.create(toDo);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdToDo.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(toDoMapper.toToDoResponse(createdToDo));
    }

    @PatchMapping("/{id}")
    ToDoResponse patch(@PathVariable long id,
                       @PathVariable("u_id") long userId,
                       @RequestBody @Valid ToDoRequest toDoRequest) {
        relationshipService.checkToDoForUser(id, userId);

        ToDo toDo= toDoService.readById(id);
        toDoMapper.patchToDoModel(toDo, toDoRequest);
        return toDoMapper.toToDoResponse(toDoService.update(toDo));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable long id,
                             @PathVariable("u_id") long userId) {
        relationshipService.checkToDoForUser(id, userId);
        toDoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
