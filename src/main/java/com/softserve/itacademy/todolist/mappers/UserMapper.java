package com.softserve.itacademy.todolist.mappers;

import com.softserve.itacademy.todolist.dto.user.UserCreateRequest;
import com.softserve.itacademy.todolist.dto.user.UserResponse;
import com.softserve.itacademy.todolist.dto.user.UserUpdateRequest;
import com.softserve.itacademy.todolist.model.Role;
import com.softserve.itacademy.todolist.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);

    User toUserFromCreate(UserCreateRequest userCreateRequest);

    void patchUserModel(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

    default String toString(Role role) {
        return role.getName();
    }
}
