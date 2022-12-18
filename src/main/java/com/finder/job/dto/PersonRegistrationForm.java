package com.finder.job.dto;

import com.finder.job.models.Person;
import com.finder.job.models.SecurityUser;
import com.finder.job.validation.NoHtml;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class PersonRegistrationForm {
    private String username;
    @NotBlank(message = "Password can't be blank")
    @NotNull(message = "Password can't be null")
    @Size(min = 8, max =  30, message = "Password must be between 8 and 30 characters")
    private String password;
    @NotBlank(message = "Password can't be blank")
    @NotNull(message = "Password can't be null")
    @Size(min = 8, max =  30, message = "Password must be between 8 and 30 characters")
    private String confirmPassword;
    @Email(message = "Email should be in right format")
    @NoHtml
    @NotBlank(message = "Email can't be blank")
    @NotNull(message = "Email can't be null")
    private String email;



    public SecurityUser toPerson(PasswordEncoder passwordEncoder) {
        return new SecurityUser(
                new Person());
    }
}
