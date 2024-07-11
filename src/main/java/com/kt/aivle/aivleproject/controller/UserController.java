package com.kt.aivle.aivleproject.controller;

import com.kt.aivle.aivleproject.dto.UserDTO;
import com.kt.aivle.aivleproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
//@RequiredArgsConstructor
@ResponseBody
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    // 생성자 주입
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    // 회원가입 페이지 출력 요청
    @GetMapping("/user/save")
    public String saveForm(UserDTO userDTO) {

        return "save";
    }

    @PostMapping("/user/save")
    public String save(@ModelAttribute UserDTO userDTO) {
        logger.info("Saving user: {}", userDTO);
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
//        userService.save(userDTO);
        userService.joinProcess(userDTO);
        return "login";
    }

    @GetMapping("/user/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute UserDTO userDTO, HttpSession session) {
        UserDTO loginResult = userService.login(userDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginUsername", loginResult.getUsername());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }
    @GetMapping("/user/")
    public String findAll(Model model) {
        List<UserDTO> userDTOList = userService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model사용
        model.addAttribute("userList", userDTOList);
        return "list";
    }

    @GetMapping("/user/{id}")
    public String findById(@PathVariable Long id, Model model) {
        UserDTO userDTO = userService.findById(id);
        model.addAttribute("user", userDTO);
        return "detail";
    }

    @GetMapping("/user/update")
    public String updateForm(HttpSession session, Model model) {
        String myUsername = (String) session.getAttribute("loginUsername");
        UserDTO userDTO = userService.updateForm(myUsername);
        model.addAttribute("updateUser", userDTO);
        return "update";
    }

    @PostMapping("/user/update")
    public String update(@ModelAttribute UserDTO userDTO) {
        userService.update(userDTO);
        return "redirect:/user/" + userDTO.getId();
    }

    @GetMapping("/user/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/user/";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @PostMapping("/user/username-check")
    public @ResponseBody String usernameCheck(@RequestParam("userName") String userName) {
        System.out.println("userName = " + userName);
        String checkResult = userService.usernameCheck(userName);
        return checkResult;
    }
}