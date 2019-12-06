DROP TABLE section CASCADE;

CREATE TABLE section(
    id              serial          PRIMARY KEY,
    name            varchar         NOT NULL
);

INSERT INTO section VALUES
(nextval('section_id_seq'), 'Electronics'),
(nextval('section_id_seq'), 'Fashion'),
(nextval('section_id_seq'), 'Home & garden'),
(nextval('section_id_seq'), 'Food Supplies'),
(nextval('section_id_seq'), 'Beauty'),
(nextval('section_id_seq'), 'Health'),
(nextval('section_id_seq'), 'Culture'),
(nextval('section_id_seq'), 'Sports'),
(nextval('section_id_seq'), 'Motorization');
