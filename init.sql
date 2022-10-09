CREATE TABLE Person
(
    person_id SERIAL       NOT NULL,
    username  VARCHAR(256) NOT NULL,
    password  VARCHAR(256) NOT NULL,
    email     VARCHAR(256) NOT NULL,
    PRIMARY KEY (person_id)
);

CREATE TABLE Skills
(
    skill_id  SERIAL       NOT NULL,
    name      VARCHAR(256) NOT NULL,
    person_id SERIAL       NOT NULL,
    PRIMARY KEY (skill_id),
    FOREIGN KEY (person_id) REFERENCES Person (person_id)
);