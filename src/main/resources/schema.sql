DROP TABLE user_role IF EXISTS;
DROP TABLE dish IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE restaurant IF EXISTS;
DROP TABLE vote IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE IF NOT EXISTS global_seq START WITH 100000;

CREATE TABLE restaurant
(
    id   INTEGER DEFAULT NEXT VALUE FOR global_seq PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT restaurant_unique_name_idx UNIQUE (name)
);

CREATE TABLE users
(
    id         INTEGER DEFAULT NEXT VALUE FOR global_seq PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    CONSTRAINT users_unique_email_idx UNIQUE (email)
);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_role_idx UNIQUE (role, user_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE dish
(
    id            INTEGER DEFAULT NEXT VALUE FOR global_seq PRIMARY KEY,
    date          DATE         NOT NULL,
    description   VARCHAR(255) NOT NULL,
    price         INTEGER      NOT NULL,
    restaurant_id INTEGER      NOT NULL,
    CONSTRAINT dish_description_price_restaurant_idx UNIQUE (description, price, restaurant_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE INDEX dish_date_restaurant_idx ON dish (date, restaurant_id);

CREATE TABLE vote
(
    id            INTEGER DEFAULT NEXT VALUE FOR global_seq PRIMARY KEY,
    date          DATE    NOT NULL,
    restaurant_id INTEGER NOT NULL,
    user_id       INTEGER NOT NULL,
    CONSTRAINT vote_date_user_idx UNIQUE (date, user_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE INDEX vote_date_restaurant_idx ON vote (date, restaurant_id);