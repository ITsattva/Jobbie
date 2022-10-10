package com.finder.job.mapper;

import com.finder.job.dto.PersonRegistrationForm;
import com.finder.job.models.Person;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Data
@Component
public class PersonMapper {

    public Person mapFormToPerson(PersonRegistrationForm form) {
        return new Person()
                .setUsername(form.getUsername())
                .setEmail(form.getEmail())
                .setPassword(form.getPassword());
    }

}
