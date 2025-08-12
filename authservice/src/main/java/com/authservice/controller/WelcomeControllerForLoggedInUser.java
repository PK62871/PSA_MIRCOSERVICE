package com.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/welcome")
public class WelcomeControllerForLoggedInUser {


    @GetMapping("/get")
    public String welcome(){
        return "Welcome";
    }
}
