package com.finder.job.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.util.List;

/**
 * Entity for mapping in database
 */
@Data
@NoArgsConstructor
@Table(name = "person")
@Entity
@Log4j2
@Accessors(chain = true)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @OneToMany(mappedBy = "person")
    private List<Skill> skills;
}
