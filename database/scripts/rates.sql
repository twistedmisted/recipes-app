CREATE TABLE ratings
(
    recipe_id    BIGINT,
    rating       FLOAT NOT NULL DEFAULT 0,
    votes_number INT   NOT NULL DEFAULT 0
);

ALTER TABLE ratings
    ADD CONSTRAINT pk_ratings PRIMARY KEY (recipe_id);
ALTER TABLE ratings
    ADD CONSTRAINT pk_ratings_recipes FOREIGN KEY (recipe_id) REFERENCES recipes (id);