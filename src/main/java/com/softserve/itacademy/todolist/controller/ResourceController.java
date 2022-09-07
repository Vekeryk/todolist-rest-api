package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.model.Priority;
import com.softserve.itacademy.todolist.model.Role;
import com.softserve.itacademy.todolist.model.State;
import com.softserve.itacademy.todolist.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ResourceController {

    private final RoleService roleService;

    @GetMapping("/roles")
    List<String> roles() {
        return roleService.getAll().stream().map(Role::getName).toList();
    }

    @GetMapping("/states")
    List<State> states() {
        return List.of(State.values());
    }

    @GetMapping("/priorities")
    List<Priority> priorities() {
        return List.of(Priority.values());
    }
}
