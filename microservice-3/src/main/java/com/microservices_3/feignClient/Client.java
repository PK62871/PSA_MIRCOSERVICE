package com.microservices_3.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "MICROSERVICE-1")
public interface Client {

    @GetMapping("/message")
    public String getData();

}