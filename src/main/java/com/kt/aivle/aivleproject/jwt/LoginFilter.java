package com.kt.aivle.aivleproject.jwt;

import com.kt.aivle.aivleproject.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        System.out.println(username);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
        return authenticationManager.authenticate(authToken);
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();
        String username = customUserDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = (GrantedAuthority)iterator.next();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        String token = this.jwtUtil.createJwt(username, role, 60*60*1000L);
//        String refreshToken = this.jwtUtil.refreshJwt(username, role, 7 * 24 * 60 * 60*1000L);

        // JWT를 response에 담아서 응답 (header 부분)
        // key: "Authorization"
        // value: "Bearer " (인증방식) + token
        response.addHeader("Authorization", "Bearer " + token);
//        response.addHeader("Authorization", "Bearer " + refreshToken);
        System.out.println("JWT Token: " + token);
//        System.out.println("JWT Token: " + refreshToken);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}
