package com.caesar.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class LoginController {

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login() {
        return "login";
    }

    @RequestMapping("/loggedin")
    @ResponseStatus(HttpStatus.OK)
    public String loggedin() {
        return "loggedin";
    }
}
