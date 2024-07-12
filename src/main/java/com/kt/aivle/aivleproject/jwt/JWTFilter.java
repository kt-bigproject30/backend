package com.kt.aivle.aivleproject.jwt;

import com.kt.aivle.aivleproject.dto.CustomUserDetails;
import com.kt.aivle.aivleproject.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authorization);
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.split(" ")[1];
            System.out.println("Extracted Token: " + token);
            if (this.jwtUtil.isExpired(token)) {
                System.out.println("token expired");
                filterChain.doFilter(request, response);
            } else {
                String username = this.jwtUtil.getUsername(token);
                String role = this.jwtUtil.getRole(token);
                System.out.println("Token Valid: Username - " + username + ", Role - " + role);
                UserEntity userEntity = new UserEntity();
                userEntity.setUsername(username);
                userEntity.setPassword("temppassword");
                userEntity.setRole(role);
                CustomUserDetails customUserDetails = new CustomUserDetails(Optional.of(userEntity));
                Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request, response);
            }
        } else {
            System.out.println("token null or invalid");
            filterChain.doFilter(request, response);
        }
    }
}
