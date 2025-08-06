package com.microservice_1.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {


    @GetMapping("/message")
    public String getMassage(){
        return "From Microservices-1";
    }
}
