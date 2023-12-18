CREATE TABLE recipes
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY,
    name        VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    status      VARCHAR(255) NOT NULL DEFAULT 'NEW',
    region_id   INT,
    category_id INT,
    hours       INT,
    minutes     INT,
    complexity  VARCHAR(50)  NOT NULL,
    user_id     BIGINT
);

ALTER TABLE recipes
    ADD CONSTRAINT pk_recipes PRIMARY KEY (id);
ALTER TABLE recipes
    ADD CONSTRAINT fk_recipes_regions FOREIGN KEY (region_id) REFERENCES regions (id);
ALTER TABLE recipes
    ADD CONSTRAINT fk_recipes_categories FOREIGN KEY (category_id) REFERENCES categories (id);
ALTER TABLE recipes
    ADD CONSTRAINT fk_recipes_users FOREIGN KEY (user_id) REFERENCES users (id);