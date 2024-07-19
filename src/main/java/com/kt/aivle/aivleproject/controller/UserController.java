package com.kt.aivle.aivleproject.controller;

import com.kt.aivle.aivleproject.dto.LoginRequest;
import com.kt.aivle.aivleproject.dto.UserDTO;
import com.kt.aivle.aivleproject.jwt.JWTUtil;
import com.kt.aivle.aivleproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt-login")
public class UserController {

    private final UserService userService;
    private final JWTUtil jwtUtil;

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

//    @GetMapping("/user/update")
//    public String updateForm(HttpSession session, Model model) {
//        String myUsername = (String) session.getAttribute("loginUsername");
//        UserDTO userDTO = userService.updateForm(myUsername);
//        model.addAttribute("updateUser", userDTO);
//        return "update";
//    }
//
//    @PostMapping("/user/update")
//    public String update(@ModelAttribute UserDTO userDTO) {
//        userService.update(userDTO);
//        return "redirect:/user/" + userDTO.getId();
//    }
//
//    @GetMapping("/user/delete/{id}")
//    public String deleteById(@PathVariable Long id) {
//        userService.deleteById(id);
//        return "redirect:/user/";
//    }
//
//    @GetMapping("/user/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "index";
//    }
//
//
//    @GetMapping("/info")
//    public String memberInfo(Authentication auth, Model model) {
//
//        UserDTO login = memberService.getLoginByUsername(auth.getName());
//
//        return "ID : " + login.getUsername() + "\n이름 : " + login.getName() + "\nrole : " + login.getRole();
//    }
//
//    @GetMapping("/admin")
//    public String adminPage(Model model) {
//
//        return "admin";
//    }
}