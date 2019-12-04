CREATE TABLE section(
    id              int             PRIMARY KEY,
    name            varchar         NOT NULL
);

CREATE TABLE category(
    id              int             PRIMARY KEY,
    name            varchar         NOT NULL,
    section_id      int             NOT NULL        REFERENCES section(id) ON DELETE CASCADE
);

CREATE TABLE auction(
    id              serial          PRIMARY KEY,
    title           varchar(255)    NOT NULL,
    description     varchar,
    price           float           NOT NULL,
    category_id     int             NOT NULL        REFERENCES category(id),
    auction_owner   varchar         NOT NULL        REFERENCES profile(username)
);

CREATE TABLE photos(
    id               serial          PRIMARY KEY,
    file_path        varchar(255)    NOT NULL,
    auction_id       int             NOT NULL        REFERENCES auction(id)
);

CREATE TABLE parameter(
    id              serial          PRIMARY KEY,
    name            varchar(64)     NOT NULL
);

CREATE TABLE auction_parameter(
    value            varchar         NOT NULL,
    parameter_id     int             NOT NULL        REFERENCES parameter(id),
    auction_id       int             NOT NULL        REFERENCES auction(id),

    PRIMARY KEY (parameter_id, auction_id)
);
