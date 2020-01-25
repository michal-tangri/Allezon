DROP TABLE cart_products;

create table cart_products (
    id          serial      PRIMARY KEY,
    amount      int,
    auction_id  int         REFERENCES auction(id) ON DELETE CASCADE,
    cart_id     int         REFERENCES carts(id) ON DELETE CASCADE
);