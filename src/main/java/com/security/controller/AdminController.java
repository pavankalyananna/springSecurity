package com.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public String adminData() {
        return "Admin API";
    }
    @GetMapping("/user")
    public String userData() {
        return "User API";
    }
    @GetMapping("/welcome")
    public String welcome() {
        return "Public API";
    }
}
