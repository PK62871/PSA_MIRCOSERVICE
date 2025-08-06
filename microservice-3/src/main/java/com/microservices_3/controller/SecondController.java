package com.microservices_3.controller;

import com.microservices_3.feignClient.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SecondController {

    @Autowired
    private RestTemplate template;

    //Inject Figen Client

    @Autowired
    private Client client;

    @GetMapping("/secondController")
    public String getMessageFromFirstController(){
        return client.getData();
    }


    //call Via RestTemplete.........
    @GetMapping("/rest")
    @LoadBalanced
    public String getMessage(){
        String uri = "http://MICROSERVICE-1/message";
        String forObject = template.getForObject(uri, String.class);
        return forObject;
    }
}
