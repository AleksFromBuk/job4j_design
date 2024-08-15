create table car_bodies
(
    id   serial primary key,
    name varchar(128)
);

create table car_engines
(
    id   serial primary key,
    name varchar(128)
);

create table car_transmissions
(
    id   serial primary key,
    name varchar(128)
);

create table cars
(
    id              serial primary key,
    name            varchar(128),
    body_id         int references car_bodies (id),
    engine_id       int references car_engines (id),
    transmission_id int references car_transmissions (id)
);

-- data insert
INSERT INTO car_bodies (name) VALUES
                                  ('Седан'),
                                  ('Хэтчбек'),
                                  ('Пикап'),
                                  ('Купе'),
                                  ('Универсал');

INSERT INTO car_engines (name) VALUES
                                   ('V8'),
                                   ('V6'),
                                   ('Электродвигатель'),
                                   ('Дизель'),
                                   ('Турбодвигатель');

INSERT INTO car_transmissions (name) VALUES
                                         ('Механика'),
                                         ('Автомат'),
                                         ('Вариатор'),
                                         ('Робот'),
                                         ('Ручная');

INSERT INTO cars (name, body_id, engine_id, transmission_id) VALUES
                                                                 ('Ford Mustang', 1, 1, 2),        -- Седан с V8 и автоматом
                                                                 ('Toyota Prius', 2, 3, 3),        -- Хэтчбек с электродвигателем и вариатором
                                                                 ('Chevrolet Silverado', 3, 4, 1), -- Пикап с дизелем и механикой
                                                                 ('Tesla Model S', 1, 3, 4),       -- Седан с электродвигателем и роботом
                                                                 ('Mazda RX-8', 4, 2, 5),          -- Купе с V6 и ручной коробкой
                                                                 ('BMW X5', 5, 5, NULL),           -- Универсал с турбодвигателем, но без коробки передач
                                                                 ('Nissan Leaf', 2, NULL, 3),      -- Хэтчбек без двигателя, но с вариатором
                                                                 ('Hyundai Elantra', 1, NULL, NULL); -- Седан без двигателя и коробки передач

-- Вывести список всех машин и все привязанные к ним детали.
-- Нужно учесть, что каких-то деталей машина может и не содержать.
-- В таком случае значение может быть null при выводе (например, название двигателя null);
--
-- Пример "шапки" при выводе:
-- id, car_name, body_name, engine_name, transmission_name

select c.name, cb.name, ce.name, ct.name from cars c
left join car_bodies cb on cb.id = c.body_id
left join car_engines ce on c.engine_id = ce.id
left join car_transmissions ct on c.transmission_id = ct.id;

-- Вывести кузова, которые не используются НИ в одной машине.
-- "Не используются" значит, что среди записей таблицы cars отсутствует
-- внешние ключи, ссылающие на таблицу car_bodies. Например, Вы добавили
-- в car_bodies "седан", "хэтчбек" и "пикап", а при добавлении в таблицу cars
-- указали только внешние ключи на записи "седан" и "хэтчбек". Запрос,
-- касающийся этого пункта, должен вывести "пикап", т.к. среди машин нет тех,
-- что обладают таким кузовом;

INSERT INTO car_bodies (name) VALUES ('Фургон');
INSERT INTO car_bodies (name) VALUES ('Грузовик');

select cb.name from car_bodies cb
left join cars c on cb.id = c.body_id
where c.id is null ;

-- Вывести двигатели, которые не используются НИ в одной машине, аналогично п.2;

INSERT INTO car_engines (name) VALUES ('Гибридный двигатель');

select ce.name from car_engines ce
                        left join cars c on ce.id = c.engine_id
where c.id is null ;

-- Вывести коробки передач, которые не используются НИ в одной машине, аналогично п.2.

INSERT INTO car_transmissions (name) VALUES ('гидродинамическая');

select ct.name from car_transmissions ct
                        left join cars c on ct.id = c.transmission_id
where c.id is null ;