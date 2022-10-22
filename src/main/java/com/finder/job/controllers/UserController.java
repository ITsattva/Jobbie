package com.finder.job.controllers;

import com.finder.job.models.vacancy.Vacancy;
import com.finder.job.util.Finder;
import com.finder.job.util.Paginator;
import com.finder.job.util.TempStorage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller with main logic of service
 */
@Controller
@RequestMapping({"", "/"})
public class UserController {
    private final Finder finder;
    private final Paginator<Vacancy> paginator;
    private final TempStorage<Vacancy> tempStorage;

    public UserController(Finder finder, Paginator<Vacancy> paginator, TempStorage<Vacancy> tempStorage) {
        this.finder = finder;
        this.paginator = paginator;
        this.tempStorage = tempStorage;
    }

    @GetMapping
    public String redirectFromRoot(){
        return "redirect:/search";
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
    public String resultPage(@RequestParam(required = false, value = "search") String query,
                             @RequestParam(required = false, value = "page") Integer page,
                             @RequestParam(required = false, value = "oldQuery") Boolean oldQuery,
                             Model model) throws IOException {
        String testRegion = "ua";//todo only for test purpose!
        List<Vacancy> vacancies;
        if(oldQuery) {
            vacancies = tempStorage.getData();
        } else {
            vacancies = finder.getVacancies(testRegion, query);
            tempStorage.storeData(vacancies);
        }
        model.addAttribute("count", vacancies.size());
        int pages = paginator.getPagesCount(vacancies);

        vacancies = page == null ? paginator.getPage(0, vacancies) : paginator.getPage(page-1, vacancies);

        model.addAttribute("vacancies", vacancies);
        model.addAttribute("pages", pages);
        model.addAttribute("page", page);

        return "result";
    }
}
