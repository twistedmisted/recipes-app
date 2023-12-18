CREATE TABLE photos
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY,
    filename  VARCHAR(255) NOT NULL,
    recipe_id BIGINT
);

ALTER TABLE photos
    ADD CONSTRAINT pk_photos PRIMARY KEY (id);
ALTER TABLE photos
    ADD CONSTRAINT fk_photos_recipes FOREIGN KEY (recipe_id) REFERENCES recipes (id);
ALTER TABLE photos
    ADD CONSTRAINT uq_photos_filename_recipe_id UNIQUE (filename, recipe_id);
