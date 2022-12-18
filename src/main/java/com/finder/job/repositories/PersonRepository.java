package com.finder.job.repositories;

import com.finder.job.models.AuthenticationType;
import com.finder.job.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findPersonByUsername(String username);

    Optional<Person> findByEmail(String email);

    @Modifying
    @Query("UPDATE Person p SET p.authType = :authType WHERE p.email = :email")
    void updateAuthenticationType(String email, AuthenticationType authType);
}
