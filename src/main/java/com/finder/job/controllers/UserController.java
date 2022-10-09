package com.finder.job.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"", "/"})
public class UserController {

    @GetMapping
    public String homePage(){
        return "homePage";
    }
}
