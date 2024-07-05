package com.kt.aivle.aivleproject.controller;

import com.kt.aivle.aivleproject.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/test")
    public String GetMappingTest(UserDTO userDTO){
        return "GetMapping : " + userDTO.PrettyPrint();
    }
}
