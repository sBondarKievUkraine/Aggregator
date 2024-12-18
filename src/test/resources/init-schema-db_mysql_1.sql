CREATE TABLE users_mysql
(
    user_id    VARCHAR(100)       NOT NULL PRIMARY KEY,
    login      VARCHAR(100)       NOT NULL,
    first_name VARCHAR(50) UNIQUE NOT NULL,
    last_name  VARCHAR(100)       NOT NULL
);
INSERT INTO users_mysql (user_id, login, first_name, last_name)
VALUES ('user-mysql-id-1', 'Hank', 'hank_t', 'Taylor'),
       ('user-mysql-id-2', 'Diana', 'diana_w', 'Williams');