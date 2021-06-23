INSERT INTO restaurant (name)
VALUES ('restaurant1'),
       ('restaurant2'),
       ('restaurant3');

INSERT INTO dish (dish_date, description, price, restaurant_id)
VALUES (CURRENT_DATE, 'dish1', 500, 100000),
       (CURRENT_DATE, 'dish2', 200, 100000),
       (CURRENT_DATE, 'dish3', 600, 100000),

       (CURRENT_DATE, 'dish4', 400, 100001),
       (CURRENT_DATE, 'dish5', 800, 100001),

       (CURRENT_DATE, 'dish6', 100, 100002),
       (CURRENT_DATE, 'dish7', 300, 100002),
       (CURRENT_DATE, 'dish8', 900, 100002);

INSERT INTO users (first_name, last_name, email, password)
VALUES ('adminFirstName', 'adminLastName', 'admin@gmail.com', '{noop}admin'),
       ('adminUserFirstName', 'adminUserLastName', 'admin_user@gmail.com', '{noop}admin_user'),
       ('firstName', 'lastName', 'user@gmail.com', '{noop}password'),
       ('firstName1', 'lastName1', 'user1@gmail.com', '{noop}password1'),
       ('firstName2', 'lastName2', 'user2@gmail.com', '{noop}password2'),
       ('firstName3', 'lastName3', 'user3@gmail.com', '{noop}password3'),
       ('firstName4', 'lastName4', 'user4@gmail.com', '{noop}password4'),
       ('firstName5', 'lastName5', 'user5@gmail.com', '{noop}password5'),
       ('firstName6', 'lastName6', 'user6@gmail.com', '{noop}password6'),
       ('firstName7', 'lastName7', 'user7@gmail.com', '{noop}password7');

INSERT INTO user_role (user_id, role)
VALUES ('100011', 'ADMIN'),
       ('100012', 'USER'),
       ('100012', 'ADMIN'),
       ('100013', 'USER'),
       ('100014', 'USER'),
       ('100015', 'USER'),
       ('100016', 'USER'),
       ('100017', 'USER'),
       ('100018', 'USER'),
       ('100019', 'USER'),
       ('100020', 'USER');

INSERT INTO vote (vote_date, restaurant_id, user_id)
VALUES (CURRENT_DATE - 2, 100000, 100013),
       (CURRENT_DATE - 2, 100002, 100014),
       (CURRENT_DATE - 2, 100001, 100015),
       (CURRENT_DATE - 2, 100001, 100016),
       (CURRENT_DATE - 2, 100002, 100017),
       (CURRENT_DATE - 2, 100000, 100018),
       (CURRENT_DATE - 2, 100000, 100019),
       (CURRENT_DATE - 2, 100001, 100020),

       (CURRENT_DATE - 1, 100001, 100013),
       (CURRENT_DATE - 1, 100001, 100014),
       (CURRENT_DATE - 1, 100001, 100015),
       (CURRENT_DATE - 1, 100002, 100016),
       (CURRENT_DATE - 1, 100002, 100017),
       (CURRENT_DATE - 1, 100001, 100018),
       (CURRENT_DATE - 1, 100000, 100019),
       (CURRENT_DATE - 1, 100002, 100020);