CREATE TABLE staffs
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY,
    name         VARCHAR(50)  NOT NULL,
    surname      VARCHAR(50)  NOT NULL,
    username     VARCHAR(50)  NOT NULL,
    password     VARCHAR(255) NOT NULL,
    access_level VARCHAR(50)  NOT NULL
);

ALTER TABLE staffs
    ADD CONSTRAINT pk_id PRIMARY KEY (id);
ALTER TABLE staffs
    ADD CONSTRAINT uq_staffs_username UNIQUE (username);