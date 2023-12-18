CREATE TABLE categories
(
    id   INT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL
);

ALTER TABLE categories
    ADD CONSTRAINT pk_categories PRIMARY KEY (id);
ALTER TABLE categories
    ADD CONSTRAINT uq_categories_name UNIQUE (name);

INSERT INTO categories
VALUES (default, 'Перші страви'),
       (default, 'Другі страви'),
       (default, 'Салати'),
       (default, 'Консервація та соління'),
       (default, 'Напої'),
       (default, 'Десерти'),
       (default, 'Випічка'),
       (default, 'Закуски'),
       (default, 'Соуси');
