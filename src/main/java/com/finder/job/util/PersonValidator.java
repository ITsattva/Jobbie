package com.finder.job.util;

import com.finder.job.dto.PersonRegistrationForm;
import com.finder.job.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonRepository personRepository;

    @Autowired
    public PersonValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PersonRegistrationForm.class.equals(clazz) || PersonRegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target.getClass().equals(PersonRegistrationForm.class)) {
            PersonRegistrationForm person = (PersonRegistrationForm) target;

            if (personRepository.findPersonByUsername(person.getUsername()).isPresent()) {
                errors.rejectValue("username", "", "This username is already taken! Try to use another");
            }
        } else if (target.getClass().equals(PersonRegistrationForm.class)) {
            PersonRegistrationForm user = (PersonRegistrationForm) target;

            if (personRepository.findPersonByUsername(user.getUsername()).isPresent()) {
                errors.rejectValue("username", "", "This username is already taken! Try to use another");
            }
        } else {
            throw new RuntimeException();
        }


    }
}
