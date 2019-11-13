CREATE SEQUENCE hibernate_sequence;

CREATE TABLE profile (
    username            VARCHAR         NOT NULL,
    name                CHAR(50)        NOT NULL,
    surname             CHAR(50)        NOT NULL,
    password            VARCHAR         NOT NULL,
    email               VARCHAR(30)     NOT NULL,
    dateOfBirth         VARCHAR(14)     NOT NULL,

    PRIMARY KEY (username)
);