CREATE TABLE IF NOT EXISTS orders (
    id          INTEGER NOT NULL AUTO_INCREMENT,
    name        VARCHAR(128) NOT NULL,
    quantity    INTEGER NOT NULL,
    PRIMARY     KEY (id)
);