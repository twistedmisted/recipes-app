CREATE TABLE users
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY,
    name     VARCHAR(50)  NOT NULL,
    surname  VARCHAR(50)  NOT NULL,
    username VARCHAR(50)  NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    verified BOOLEAN      NOT NULL DEFAULT false
);

ALTER TABLE users
    ADD CONSTRAINT pk_users PRIMARY KEY (id);
ALTER TABLE users
    ADD CONSTRAINT uq_users_username UNIQUE (username);
ALTER TABLE users
    ADD CONSTRAINT uq_users_email UNIQUE (email);