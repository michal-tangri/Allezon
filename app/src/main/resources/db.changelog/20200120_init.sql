DROP TABLE cart_products;
DROP TABLE carts;

create table carts (
    id          serial      PRIMARY KEY,
    created_at  date        NOT NULL,
    username    varchar     NOT NULL    UNIQUE REFERENCES profile(username)
);

create table cart_products (
    id          serial      PRIMARY KEY,
    auction_id  int         REFERENCES auction(id),
    cart_id     int         REFERENCES carts(id)
);