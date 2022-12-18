CREATE TABLE Person
(
    person_id SERIAL       NOT NULL,
    username  VARCHAR(256) NOT NULL,
    password  VARCHAR(256),
    email     VARCHAR(256) NOT NULL,
    auth_type VARCHAR(256) NOT NULL,
    PRIMARY KEY (person_id)
);

CREATE TABLE Skill
(
    skill_id  SERIAL       NOT NULL,
    name      VARCHAR(256) NOT NULL,
    person_id SERIAL       NOT NULL,
    PRIMARY KEY (skill_id),
    FOREIGN KEY (person_id) REFERENCES Person (person_id)
);