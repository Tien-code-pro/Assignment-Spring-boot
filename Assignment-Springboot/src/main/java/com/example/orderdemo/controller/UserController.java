package com.example.orderdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloUser () {
        return "hello account with role user";
    }
}
