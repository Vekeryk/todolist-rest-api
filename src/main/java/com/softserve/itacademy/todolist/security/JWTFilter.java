package com.softserve.itacademy.todolist.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            try {
                String jwt = authHeader.substring(7);
                jwtUtil.validateToken(jwt);
                long userId = jwtUtil.getIdFormToken(jwt);
                SimpleGrantedAuthority userRole = jwtUtil.getRoleFromToken(jwt);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userId, null, Collections.singletonList(userRole));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                log.error("Request for '{} {}' failed with error: {} \n {}", request.getMethod(), request.getRequestURL(), e.getClass(), e.getMessage());
                response.sendError(UNAUTHORIZED.value(), "Invalid token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
