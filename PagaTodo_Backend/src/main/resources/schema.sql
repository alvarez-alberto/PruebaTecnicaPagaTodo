CREATE TABLE if not exists user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    isActive BOOLEAN NOT NULL,
    roles JSONB
);