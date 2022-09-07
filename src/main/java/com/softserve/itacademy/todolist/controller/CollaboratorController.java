package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.user.UserResponse;
import com.softserve.itacademy.todolist.mappers.UserMapper;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{u_id}/todos/{t_id}/collaborators")
public class CollaboratorController {

    private final UserService userService;
    private final ToDoService toDoService;
    private final UserMapper userMapper;

    @PreAuthorize("""
            (hasAuthority('ADMIN') or authentication.principal == #userId or
            @toDoServiceImpl.isUserCollaborator(#toDoId, authentication.principal))
            and @relationshipServiceImpl.isRelatedToDoForUser(#toDoId, #userId)""")
    @GetMapping
    List<UserResponse> readAll(@PathVariable("u_id") long userId,
                               @PathVariable("t_id") long toDoId) {
        return userMapper.toUserResponseList(toDoService.readById(toDoId).getCollaborators());
    }

    @PreAuthorize("""
            (hasAuthority('ADMIN') or authentication.principal == #userId) and
            @relationshipServiceImpl.isRelatedToDoForUser(#toDoId, #userId)""")
    @PostMapping("/{id}")
    List<UserResponse> add(@PathVariable("id") long collaboratorId,
                           @PathVariable("u_id") long userId,
                           @PathVariable("t_id") long toDoId) {
        ToDo todo = toDoService.readById(toDoId);
        toDoService.addCollaborator(todo, userService.readById(collaboratorId));
        return userMapper.toUserResponseList(todo.getCollaborators());
    }

    @PreAuthorize("""
            (hasAuthority('ADMIN') or authentication.principal == #userId) and
            @relationshipServiceImpl.isRelatedToDoForUser(#toDoId, #userId)""")
    @DeleteMapping("/{id}")
    List<UserResponse> remove(@PathVariable("id") long collaboratorId,
                              @PathVariable("u_id") long userId,
                              @PathVariable("t_id") long toDoId) {
        ToDo todo = toDoService.readById(toDoId);
        toDoService.removeCollaborator(todo, userService.readById(collaboratorId));
        return userMapper.toUserResponseList(todo.getCollaborators());
    }
}
