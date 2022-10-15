package com.finder.job.dto;

import com.finder.job.models.Person;
import com.finder.job.models.SecurityUser;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class PersonRegistrationForm {
    private String username;
    private String email;
    private String password;



    public SecurityUser toPerson(PasswordEncoder passwordEncoder) {
        return new SecurityUser(
                new Person());
    }
}
