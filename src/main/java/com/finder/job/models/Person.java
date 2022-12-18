package com.finder.job.models;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Entity for mapping in database
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "person")
@Entity
@Log4j2
@Accessors(chain = true)
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column
    private String username;

    @Column
    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "person")
    private List<Skill> skills;
}
