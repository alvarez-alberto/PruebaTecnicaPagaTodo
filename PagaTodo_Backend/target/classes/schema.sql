DROP TABLE USERS;

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    account_non_expired BOOLEAN NOT NULL,
    credentials_non_expired BOOLEAN NOT NULL,
    account_non_locked BOOLEAN NOT NULL
);

GRANT INSERT, UPDATE, DELETE, SELECT ON TABLE users TO postgres;
-- Insertar usuarios dummy
INSERT INTO users (username, password, enabled, account_non_expired, credentials_non_expired, account_non_locked)
VALUES ('user1', 'password1', true, true, true, true),
       ('user2', 'password2', false, true, true, true),
       ('user3', 'password3', true, false, true, true),
       ('user4', 'password4', true, true, false, true);