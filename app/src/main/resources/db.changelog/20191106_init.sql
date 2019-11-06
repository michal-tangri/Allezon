CREATE TABLE auction(
    id          serial          PRIMARY KEY,
    title       varchar(255)    NOT NULL,
    description varchar,
    price       float           NOT NULL
);

CREATE TABLE photos(
    id          serial          PRIMARY KEY,
    filepath    varchar(255)    NOT NULL,
    auction     int             NOT NULL        REFERENCES auction(id)
);

CREATE TABLE parameter(
    id          serial          PRIMARY KEY,
    name        varchar(64)     NOT NULL
);


CREATE TABLE auction_parameter(
    id              serial          NOT NULL,
    value           varchar         NOT NULL,
    parameter_id    int         NOT NULL            REFERENCES parameter(id),
    auction_id      int             NOT NULL        REFERENCES auction(id)
);