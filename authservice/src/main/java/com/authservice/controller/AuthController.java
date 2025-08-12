package com.authservice.controller;

import com.authservice.DTO.APIResponse;
import com.authservice.DTO.LoginDto;
import com.authservice.DTO.UserDto;
import com.authservice.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

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

/*
*****************************************************************************************
How the flow works when you pass username & password
User sends credentials → /login (or custom endpoint).

Spring Security Filter intercepts the request.

UsernamePasswordAuthenticationToken is created with your input.

AuthenticationManager delegates to DaoAuthenticationProvider.

DaoAuthenticationProvider calls:

userDetailsService.loadUserByUsername(username)

passwordEncoder.matches(rawPassword, encodedPasswordFromDB)

If credentials match → creates an Authentication object → marks the user as logged in.

If credentials don’t match → throws BadCredentialsException.

*********************************************************************************************
*********************************************************************************************
*********************************************************************************************
[Request with username/password]
           ↓
[UsernamePasswordAuthenticationFilter]
           ↓
[AuthenticationManager]
           ↓
[DaoAuthenticationProvider]
           ↓
[UserDetailsService.loadUserByUsername()]
           ↓
[PasswordEncoder.matches()]
           ↓
 ┌─────────────┐       ┌─────────────┐
 │ Success ✔   │       │ Failure ❌  │
 └───────┬─────┘       └──────┬──────┘
         ↓                    ↓
 [SecurityContext]       401 Unauthorized


*/

    //Users Login API..............
    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> login(@RequestBody LoginDto loginDto){

        APIResponse<String> response = new APIResponse<>();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());

        try {
            Authentication authenticated = authenticationManager.authenticate(token);

            if (authenticated.isAuthenticated()){
                response.setMessage("Login Successfully");
                response.setStatus(200);
                response.setData("User has logged");

                return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        response.setData("Unauthorize-access");
        response.setStatus(401);
        response.setMessage("Login Failed");
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
    }
}
