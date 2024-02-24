package com.moyeoba.project.security.jwt;

import com.moyeoba.project.token.TokenManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Access: {}", request.getRequestURI());
        log.info("isOpen?: {}", PermittedPath.isOpen(request.getRequestURI()));
        if(PermittedPath.isOpen(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = "";
        try {
            token = tokenManager.resolveToken(request);
        } catch (Exception e) {
            System.out.println("Rejected: " + request.getRequestURI() + " has no token.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Token Not Found");
            return;
        }


        if(token != null && tokenManager.validateToken(token)) {
            try {
                Authentication authentication = tokenManager.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthenticationException ex) {
                log.error("AuthenticationException: {}", ex.toString());
                jwtAuthenticationEntryPoint.commence(request, response, ex);
            }

        } else {
            System.out.println("There is No Valid Token.");
            throw new AccessDeniedException("Access Denied.");
        }

        filterChain.doFilter(request, response);
    }
}
