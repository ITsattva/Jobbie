package com.finder.job.controllers;

import com.finder.job.dto.PersonRegistrationForm;
import com.finder.job.models.Person;
import com.finder.job.services.PersonService;
import com.finder.job.util.PersonValidator;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;


/**
 * Controller for authorization/registration process
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonService personService;
    private final PersonValidator personValidator;

    public AuthController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }


    @GetMapping("/login")
    public String login(){

        System.out.println("AuthController method login");

        return "/auth/login";
    }

    @GetMapping("/success")
    public String successRedirect() {

        System.out.println("AuthController method successRedirect");

        return "redirect:/search";
    }

    @GetMapping("/registration")
    public String registrationPerson(@ModelAttribute("form") PersonRegistrationForm form) {

        System.out.println("AuthController method registrationPerson");
        System.out.println(form.toString());

        return "/auth/registration";
    }
    @PostMapping("/registration")
    public String registrationPersonDone(@Valid @ModelAttribute("form") PersonRegistrationForm form, BindingResult bindingResult) {

        personValidator.validate(form, bindingResult);


        if (bindingResult.hasErrors())
            return "registration";

        personService.registration(form);

        System.out.println("AuthController method registrationPersonDONE");
        System.out.println(form.toString());

        return "redirect:/auth/login";
    }

}
