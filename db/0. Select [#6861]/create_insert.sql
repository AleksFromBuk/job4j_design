-- create table passport
-- (
--     id     serial primary key,
--     seria  int,
--     number int
-- );
--
-- create table people
-- (
--     id          serial primary key,
--     name        varchar(255),
--     passport_id int references passport (id) unique
-- );
--
-- -- insert values
-- insert into passport(seria, number)
-- values (1111, 123456);
--
-- insert into passport(seria, number)
-- values (1112, 123457);
-- insert into passport(seria, number)
-- values (1113, 123458);
--
-- insert into people(name, passport_id)
-- values ('Ivan', 1);
-- insert into people(name, passport_id)
-- values ('Boris', 2);
-- insert into people(name, passport_id)
-- values ('Petr', 3);
-- insert into people(name)
-- values ('Vasya');
-- insert into people(name)
-- values ('Anya');

-- join
-- select *
-- from people
--          inner join passport on people.passport_id = passport.id;
--
-- select * from people join passport on people.passport_id = passport.id;

-- uses 'as':

-- select pp.name, p.seria, p.number
-- from people as pp
--          join passport as p on pp.passport_id = p.id;
--
-- select pp.name as Имя, p.seria as Серия, p.number as Номер
-- from people as pp
--          join passport as p on pp.passport_id = p.id;
--
-- select pp.name as "Имя владельца", p.seria Серия, p.number as Номер
-- from people pp
--          join passport p on pp.passport_id = p.id;

-- #task

create table shelfs_on_shkafs
(
    id                     serial primary key,
    first_symbol_of_author char,
    number_of_shelf        int
);

create table books
(
    id       serial primary key,
    name     varchar(128),
    author   varchar(128),
    shelf_id int references shelfs_on_shkafs (id)
);

-- insert
insert into shelfs_on_shkafs(first_symbol_of_author, number_of_shelf)
values ('I', 1), -- Isaac Newton
       ('A', 2), -- Anthony the Great
       ('R', 3), -- Ren? Descartes
       ('G', 4), -- Gottfried Wilhelm Leibniz
       ('J', 5), -- Jacob Bernoulli
       ('C', 6), -- Carl Friedrich Gauss
       ('H', 7); -- Henri Poincar?


insert into books(name, author, shelf_id)
values ('Philosophiae Naturalis Principia Mathematica', 'Isaac Newton', 1),
       ('Discourse on the Method', 'Ren? Descartes', 3),
       ('The Mathematical Papers of Isaac Newton', 'Isaac Newton', 2),
       ('New Essays on Human Understanding', 'Gottfried Wilhelm Leibniz', 4),
       ('Ars Conjectandi', 'Jacob Bernoulli', 5),
       ('Disquisitiones Arithmeticae', 'Carl Friedrich Gauss', 6),
       ('Science and Method', 'Henri Poincar?', 7),
       ('The Foundations of Science', 'Henri Poincar?', 7),
       ('Writings of Anthony the Great', 'Anthony the Great', 1),
       ('Homilies of St. Macarius the Great', 'Macarius of Egypt',
        1),
       ('Spiritual Psalter', 'Ephrem the Syrian', 1),
       ('Ascetical Homilies of Saint Isaac the Syrian', 'Isaac the Syrian', 1); 

select b.name "Outstanding work", bs.number_of_shelf Shelf
from books as b
         join shelfs_on_shkafs as bs on b.shelf_id = bs.id;


select b.name             "literature for serious guys",
       b.author           "Outstanding person",
       bs.number_of_shelf "A shelf for a book by this Outstanding Man"
from books as b
         join shelfs_on_shkafs as bs on b.shelf_id = bs.id;


select b.name             "literature for serious guys",
       b.author           "Outstanding person",
       bs.number_of_shelf "A shelf for a book by this Outstanding Man"
from books as b
         join shelfs_on_shkafs as bs on b.shelf_id = bs.id and (b.author like '%Anthony%' or b.author like '%Syrian%' or
                                                                b.author like '%Macarius%');