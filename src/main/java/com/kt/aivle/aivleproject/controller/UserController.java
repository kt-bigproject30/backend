package com.kt.aivle.aivleproject.controller;

import com.kt.aivle.aivleproject.dto.LoginRequest;
import com.kt.aivle.aivleproject.dto.UserDTO;
import com.kt.aivle.aivleproject.jwt.JWTUtil;
import com.kt.aivle.aivleproject.jwt.JwtBlacklist;
import com.kt.aivle.aivleproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt-login")
public class UserController {

    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final JwtBlacklist jwtBlacklist;

    @GetMapping(value = {"", "/"})
    public String home(Model model) {

        model.addAttribute("loginType", "jwt-login");
        model.addAttribute("pageName", "스프링 시큐리티 JWT 로그인");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        UserDTO login = userService.getLoginByUsername(username);

        if (login != null) {
            model.addAttribute("name", login.getName());
        }

        return "home";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {

        model.addAttribute("loginType", "jwt-login");
        model.addAttribute("pageName", "스프링 시큐리티 JWT 로그인");

        // 회원가입을 위해서 model 통해서 joinRequest 전달
        model.addAttribute("userDTO", new UserDTO());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @RequestBody UserDTO userDTO,
                       BindingResult bindingResult, Model model) {

        model.addAttribute("loginType", "jwt-login");
        model.addAttribute("pageName", "스프링 시큐리티 JWT 로그인");

        // ID 중복 여부 확인
        if (userService.checkUsernameDuplicate(userDTO.getUsername())) {
            return "ID가 존재합니다.";
        }


        // 비밀번호 = 비밀번호 체크 여부 확인
        if (!userDTO.getPassword().equals(userDTO.getPasswordCheck())) {
            return "비밀번호가 일치하지 않습니다.";
        }

        // 에러가 존재하지 않을 시 joinRequest 통해서 회원가입 완료
        userService.securityJoin(userDTO);

        // 회원가입 시 홈 화면으로 이동
        return "redirect:/jwt-login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginType", "jwt-login");
        model.addAttribute("pageName", "스프링 시큐리티 JWT 로그인");
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){

        UserDTO userDTO = userService.login(loginRequest);


        if(userDTO==null){
            return "ID 또는 비밀번호가 일치하지 않습니다!";
        }

        String token = jwtUtil.createJwt(userDTO.getUsername(), userDTO.getRole(), 1000*60*60L);
        return token;
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.split(" ")[1];
            jwtBlacklist.addToken(token);
            return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/user")
    public ResponseEntity<Map<String, String>> getCurrentUserName() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO userDTO = userService.getLoginByUsername(username);

        Map<String, String> response = new HashMap<>();
        if (userDTO != null) {
            response.put("name", userDTO.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("error", "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}