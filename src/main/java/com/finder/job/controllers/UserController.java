package com.finder.job.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Controller with main logic of service
 */
@Controller
@RequestMapping({"", "/"})
public class UserController {

    @GetMapping("/homepage")
    public String homePage(){
        return "homePage";
    }

    @GetMapping("/search")
    public String searchPage(){
        return "search";
    }

    @GetMapping("/result")
    public String resultPage(){
        return "result";
    }
}
