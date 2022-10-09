package com.finder.job.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Table(name = "skill")
@Entity
@Log4j2
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long id;

    @Column
    private String name;

    @ManyToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person person;
}
