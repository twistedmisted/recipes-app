CREATE TABLE videos
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY,
    filename  VARCHAR(255) NOT NULL,
    recipe_id BIGINT
);

ALTER TABLE videos
    ADD CONSTRAINT pk_videos PRIMARY KEY (id);
ALTER TABLE videos
    ADD CONSTRAINT fk_videos_recipes FOREIGN KEY (recipe_id) REFERENCES recipes (id);
ALTER TABLE videos
    ADD CONSTRAINT uq_videos_filename_recipe_id UNIQUE (filename, recipe_id);
