package com.softserve.itacademy.todolist.config;

import com.softserve.itacademy.todolist.service.RelationshipService;
import com.softserve.itacademy.todolist.service.impl.RelationshipServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.softserve.itacademy.todolist.security")
@Import(SecurityConfig.class)
public class TestSecurityConfig {
}
