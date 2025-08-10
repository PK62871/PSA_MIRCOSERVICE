package com.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class MessageController {


    //URL :- http://localhost:8080/api/v1/auth/welcome
    //Open URL
    @GetMapping("/welcome")
    public String getWelcome(){
        return "Welcome";
    }


    //URL :- http://localhost:8080/api/v1/auth/hello
    //Protected URL
    @GetMapping("/hello")
    public String getHello(){
        return "Hello";
    }
}
