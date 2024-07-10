package com.kt.aivle.aivleproject.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

@Controller
//@ResponseBody
public class HomeController {


    @GetMapping("/")
    public String index() {
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();
        return "index";
    }

//    @GetMapping({"/"})
//    public String mainP() {
//        return "Main Controller";
//    }
//
//    @GetMapping({"/admin"})
//    public String adminP() {
//        return "Admin Controller";
//    }
}