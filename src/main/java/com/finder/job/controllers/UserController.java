package com.finder.job.controllers;

import com.finder.job.models.vacancy.Vacancy;
import com.finder.job.util.Finder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * Controller with main logic of service
 */
@Controller
@RequestMapping({"", "/"})
public class UserController {
    private final Finder finder;

    public UserController(Finder finder) {
        this.finder = finder;
    }

    @GetMapping("/homepage")
    public String homePage(){
        return "homePage";
    }

    @GetMapping("/search")
    public String searchPage(){
        return "search";
    }

    @GetMapping("/result")
    public String redirectToSearch(){
        return "redirect:/search";
    }
    @PostMapping("/result")
    public String resultPage(@RequestParam("search")String query, Model model) throws IOException {
        String testRegion = "ua";//todo only for test purpose!
        List<Vacancy> vacancies = finder.getVacancies(testRegion, query);
        model.addAttribute("count", vacancies.size());
        model.addAttribute("vacancies", vacancies);

        return "result";
    }
}
