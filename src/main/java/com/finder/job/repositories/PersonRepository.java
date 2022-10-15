package com.finder.job.repositories;

import com.finder.job.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findPersonByUsername(String username);

//    Optional<Person> findById(BigInteger bigInteger);
}
