package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.todo.ToDoRequest;
import com.softserve.itacademy.todolist.dto.todo.ToDoResponse;
import com.softserve.itacademy.todolist.mappers.ToDoMapper;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ToDoMapper toDoMapper;

    @PreAuthorize("hasAuthority('ADMIN') or authentication.principal == #userId")
    @GetMapping
    List<ToDoResponse> getAll(@PathVariable("u_id") long userId) {
        return toDoMapper.toToDoResponseList(toDoService.getByUserId(userId));
    }

    @PreAuthorize("""
            @relationshipServiceImpl.isRelatedToDoForUser(#id, #userId) and
            (hasAuthority('ADMIN') or authentication.principal == #userId
            or @toDoServiceImpl.isUserCollaborator(#id, authentication.principal))""")
    @GetMapping("/{id}")
    ToDoResponse read(@PathVariable long id,
                      @PathVariable("u_id") long userId) {
        return toDoMapper.toToDoResponse(toDoService.readById(id));
    }

    @PostMapping
    @PreAuthorize("(hasAuthority('ADMIN') or authentication.principal == #userId)")
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

    @PreAuthorize("""
            (hasAuthority('ADMIN') or authentication.principal == #userId) and
            @relationshipServiceImpl.isRelatedToDoForUser(#id, #userId)""")
    @PatchMapping("/{id}")
    ToDoResponse patch(@PathVariable long id,
                       @PathVariable("u_id") long userId,
                       @RequestBody @Valid ToDoRequest toDoRequest) {
        ToDo toDo = toDoService.readById(id);
        toDoMapper.patchToDoModel(toDo, toDoRequest);
        return toDoMapper.toToDoResponse(toDoService.update(toDo));
    }

    @PreAuthorize("""
            (hasAuthority('ADMIN') or authentication.principal == #userId) and
            @relationshipServiceImpl.isRelatedToDoForUser(#id, #userId)""")
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable long id,
                             @PathVariable("u_id") long userId) {
        toDoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
