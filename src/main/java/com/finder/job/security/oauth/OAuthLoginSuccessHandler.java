package com.finder.job.security.oauth;

import com.finder.job.models.AuthenticationType;
import com.finder.job.models.Person;
import com.finder.job.repositories.PersonRepository;
import com.finder.job.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final PersonRepository personRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        if (personRepository.findByEmail(oAuth2User.getEmail()).isEmpty()) {
            Person person = new Person()
                    .setUsername(oAuth2User.getName())
                    .setEmail(oAuth2User.getEmail())
                    .setPassword("")
                    .setAuthType(AuthenticationType.valueOf(oAuth2User.getOauth2ClientName().toUpperCase()));
            personRepository.save(person);
        } else {
            personRepository.updateAuthenticationType(oAuth2User.getEmail(), AuthenticationType.valueOf(oAuth2User.getOauth2ClientName().toUpperCase()));
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
