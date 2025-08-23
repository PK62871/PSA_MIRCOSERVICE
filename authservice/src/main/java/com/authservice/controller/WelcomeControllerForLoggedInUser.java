package com.authservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/welcome")
public class WelcomeControllerForLoggedInUser {


    //That endpoint can access anyone (for all roles).............................
    @GetMapping("/get")
    public String welcome(){

        //Get the name of logged-in user from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        return "Welcome " + name;
    }

    //That endpoint will only for ADMIN Roles.................
    @GetMapping("/admin")
    public String adminWelcome(){

        //Get the name of logged-in user from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return "Welcome Admin " + name;
    }
}
