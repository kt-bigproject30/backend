package com.kt.aivle.aivleproject.controller;

import com.kt.aivle.aivleproject.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/user")
public class UserController {
    @PostMapping("/signup")
    public String GetMappingTest(){
        return "GetMapping : hello";
    }
}
