package com.finder.job.services;

import com.finder.job.dto.PersonRegistrationForm;
import com.finder.job.mapper.PersonMapper;
import com.finder.job.models.AuthenticationType;
import com.finder.job.models.Person;
import com.finder.job.repositories.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class PersonService {
    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    public PersonService(PersonMapper personMapper, PasswordEncoder passwordEncoder, PersonRepository personRepository) {
        this.personMapper = personMapper;
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    public void registration(PersonRegistrationForm form) {
        Person person = personMapper.mapFormToPerson(form);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setAuthType(AuthenticationType.DATABASE);
        personRepository.save(person);
    }

}
