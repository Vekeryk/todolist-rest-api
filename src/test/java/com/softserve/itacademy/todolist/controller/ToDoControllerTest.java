package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.config.TestSecurityConfig;
import com.softserve.itacademy.todolist.config.WithMockCustomUser;
import com.softserve.itacademy.todolist.mappers.ToDoMapper;
import com.softserve.itacademy.todolist.security.JWTFilter;
import com.softserve.itacademy.todolist.service.RelationshipService;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import com.softserve.itacademy.todolist.service.impl.RelationshipServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ToDoController.class)
//@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
@MockBean({ToDoMapper.class, UserService.class})
class ToDoControllerTest {
    @MockBean
    private ToDoService toDoService;
    @MockBean(name="relationshipServiceImpl")
    private RelationshipService relationshipService;
    @MockBean
    private Authentication authentication;
    @Autowired
    private MockMvc mockMvc;

    @Nested
    class DeleteToDos {
        @Test
        @WithAnonymousUser
        void shouldRespondUnauthorized_forAnonymousUser() throws Exception {
            mockMvc.perform(get("/api/users/{u_id}/todos", 1L))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @WithMockUser(authorities = "ADMIN")
        void shouldGetAllAndRespondOk_forAdmin() throws Exception {
            mockMvc.perform(get("/api/users/{u_id}/todos", 1L))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(authorities = "ADMIN")
        void shouldRespondNotFound_whenUserIdIsNotFound_forAdmin() throws Exception {
            when(toDoService.getByUserId(anyLong())).thenThrow(new EntityNotFoundException());
            mockMvc.perform(get("/api/users/{u_id}/todos", 1L))
                    .andExpect(status().isNotFound());
        }

        @Test
        @WithMockCustomUser
        void shouldGetAllAndRespondOk_forOwner() throws Exception {
            mockMvc.perform(get("/api/users/{u_id}/todos", 1L))
                    .andExpect(status().isOk());
        }


    }


    @Test
    void read() {
    }

    @Test
    void create() {
    }

    @Test
    void patch() {
    }


    @Nested
    class DeleteToDo {
        @Test
        @WithAnonymousUser
        void shouldRespondUnauthorized_forAnonymousUser() throws Exception {
            mockMvc.perform(get("/api/users/{u_id}/todos", 1L))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @WithMockUser(authorities = "ADMIN")
        void shouldDeleteAndRespondNoContent_whenToDoExistsAndRelatedToUser_forAdmin() throws Exception {
            doNothing().when(toDoService).delete(anyInt());
            when(relationshipService.isRelatedToDoForUser(anyLong(), anyLong())).thenReturn(true);

            mockMvc.perform(delete("/api/users/{u_id}/todos/{id}", 1L, 1L))
                    .andExpect(status().isNoContent());
        }

        @Test
        @WithMockUser(authorities = "ADMIN")
        void shouldRespondForbidden_whenToDoIsNotRelatedToUser_forAdmin() throws Exception {
            when(relationshipService.isRelatedToDoForUser(anyLong(), anyLong())).thenReturn(false);

            mockMvc.perform(delete("/api/users/{u_id}/todos/{id}", 1L, 1L))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(authorities = "ADMIN")
        void shouldThrowAndRespondNotFound_whenIdNotFound_forAdmin() throws Exception {
            when(relationshipService.isRelatedToDoForUser(anyLong(), anyLong())).thenReturn(true);
            doThrow(new EntityNotFoundException()).when(toDoService).delete(anyLong());

            mockMvc.perform(delete("/api/users/{u_id}/todos/{id}", 1L, 1L))
                    .andExpect(status().isNotFound());
        }

        @Test
        @WithMockCustomUser()
        void shouldDeleteAndRespondNoContent_forOwner() throws Exception {
            when(relationshipService.isRelatedToDoForUser(anyLong(), anyLong())).thenReturn(true);

            mockMvc.perform(delete("/api/users/{u_id}/todos/{id}", 1L, 1L))
                    .andExpect(status().isNoContent());
        }

        @Test
        @WithMockCustomUser()
        void shouldRespondForbidden_whenToDoIsNotRelatedToUser_forOwner() throws Exception {
            when(relationshipService.isRelatedToDoForUser(anyLong(), anyLong())).thenReturn(false);

            mockMvc.perform(delete("/api/users/{u_id}/todos/{id}", 1L, 1L))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockCustomUser
        void shouldThrowAndRespondNotFound_whenIdNotFound_forOwner() throws Exception {
            when(relationshipService.isRelatedToDoForUser(anyLong(), anyLong())).thenReturn(true);
            doThrow(new EntityNotFoundException()).when(toDoService).delete(anyLong());

            mockMvc.perform(delete("/api/users/{u_id}/todos/{id}", 1L, 1L))
                    .andExpect(status().isNotFound());
        }
    }
}