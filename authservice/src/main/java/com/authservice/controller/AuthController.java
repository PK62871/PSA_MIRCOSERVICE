package com.authservice.controller;

import com.authservice.DTO.APIResponse;
import com.authservice.DTO.UserDto;
import com.authservice.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


//    //URL :- http://localhost:8080/api/v1/auth/welcome
//    //Open URL
//    @GetMapping("/welcome")
//    public String getWelcome(){
//        return "Welcome";
//    }
//
//
//    //URL :- http://localhost:8080/api/v1/auth/hello
//    //Protected URL
//    @GetMapping("/hello")
//    public String getHello(){
//        return "Hello";
//    }

    //User Registration API................
    @PostMapping("/register")
    public ResponseEntity<APIResponse<String>> register(@RequestBody UserDto userDto){

        APIResponse<String> response = authService.register(userDto);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }
}
