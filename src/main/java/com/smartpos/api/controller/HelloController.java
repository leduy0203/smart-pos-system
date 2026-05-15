package com.smartpos.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String hello() {
        return "Hello Shopeex Backend Service HI i am duy !";
    }

    @GetMapping("/test")
    public String test() {
        return "Test endpoint successful";
    }

}
