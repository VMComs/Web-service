package ru.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    @GetMapping("/admin")
    public String adminPage() {
        return "admin-panel";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user-page";
    }


}
