CREATE TABLE regions
(
    id   INT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL
);

ALTER TABLE regions
    ADD CONSTRAINT pk_regions PRIMARY KEY (id);
ALTER TABLE regions
    ADD CONSTRAINT uq_regions_name UNIQUE (name);

INSERT INTO regions
VALUES (default, 'Бессарабія'),
       (default, 'Буковина'),
       (default, 'Волинь'),
       (default, 'Галичина'),
       (default, 'Донеччина'),
       (default, 'Закарпаття'),
       (default, 'Карпати'),
       (default, 'Київ'),
       (default, 'Наддніпрянщина'),
       (default, 'Поділля'),
       (default, 'Подніпров`я та Запоріжжя'),
       (default, 'Полісся'),
       (default, 'Полтавщина'),
       (default, 'Приазов`я'),
       (default, 'Причорномор`я'),
       (default, 'Сіверщина'),
       (default, 'Слобожанщина'),
       (default, 'Таврія');
