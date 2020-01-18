create table carts (
    id          serial      PRIMARY KEY,
    username    VARCHAR     REFERENCES profile(username) NOT NULL
);

create table cart_products (
    id          serial      PRIMARY KEY,
    added_at    date        NOT NULL,
    auction_id  int         REFERENCES auction(id),
    cart_id     int         REFERENCES carts(id)
);