package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.user.UserCreateRequest;
import com.softserve.itacademy.todolist.dto.user.UserResponse;
import com.softserve.itacademy.todolist.dto.user.UserUpdateRequest;
import com.softserve.itacademy.todolist.mappers.UserMapper;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    List<UserResponse> getAll() {
        return userMapper.toUserResponseList(userService.getAll());
    }

    @GetMapping("/{id}")
    UserResponse read(@PathVariable long id) {
        return userMapper.toUserResponse(userService.readById(id));
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        User user = userService.create(userMapper.toUserFromCreate(userCreateRequest));

        return ResponseEntity
                .created(URI.create(String.format("/users/%d", user.getId())))
                .body(userMapper.toUserResponse(user));
    }

    @PatchMapping("/{id}")
    UserResponse patch(@PathVariable long id, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        User user = userService.readById(id);
        userMapper.patchUserModel(user, userUpdateRequest);

        return userMapper.toUserResponse(userService.update(user));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
