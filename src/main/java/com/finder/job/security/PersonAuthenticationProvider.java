package com.finder.job.security;

import com.finder.job.models.Person;
import com.finder.job.repositories.PersonRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PersonAuthenticationProvider implements AuthenticationProvider {

    private final PersonRepository repository;
    private final PasswordEncoder passwordEncoder;

    public PersonAuthenticationProvider(PersonRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Person person = repository.findPersonByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("There is no user with this username"));

        if (passwordEncoder.matches(password, person.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password);
        } else {
            throw new BadCredentialsException("Invalid password!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
