package com.bbd.bursary.manager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/testing")
public class TestingController {

    @GetMapping
    public String sayHello() {
        return "Hello";
    }
}
