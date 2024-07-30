-- create table students
-- (
--     id   serial primary key,
--     name text
-- );
--
-- create table subjects
-- (
--     id   serial primary key,
--     name text
-- );
--
-- create table students_subjects
-- (
--     id         serial primary key,
--     mark       float,
--     student_id int references students (id),
--     subject_id int references subjects (id)
-- );
--
-- -- insert
--
-- insert into students(name)
-- values ('???'),
--        ('????'),
--        ('????');
-- insert into subjects(name)
-- values ('??????????'),
--        ('???????'),
--        ('???????????');
-- insert into students_subjects(student_id, subject_id, mark)
-- values (1, 1, 5),
--        (1, 2, 5),
--        (1, 3, 5);
-- insert into students_subjects(student_id, subject_id, mark)
-- values (2, 1, 5),
--        (2, 2, 4),
--        (2, 3, 4);
-- insert into students_subjects(student_id, subject_id, mark)
-- values (3, 1, 3),
--        (3, 2, 5),
--        (3, 3, 3);
--
-- select avg(mark)
-- from students_subjects;
-- select min(mark)
-- from students_subjects;
-- select max(mark)
-- from students_subjects;
--
-- select s.name, avg(ss.mark)
-- from students_subjects as ss
--          join subjects s on ss.subject_id = s.id
-- group by s.name;
--
-- select s.name, avg(ss.mark)
-- from students_subjects as ss
--          join students s on ss.student_id = s.id
-- group by s.name;
--
-- select s.name, avg(ss.mark)
-- from students_subjects as ss
--          join subjects s on ss.subject_id = s.id
-- group by s.name
-- having avg(ss.mark) > 4.2;

--                  task 2. Группировка и агрегатные функции [#373618]

create table devices
(
    id    serial primary key,
    name  varchar(255),
    price float
);

create table people
(
    id   serial primary key,
    name varchar(255)
);

create table devices_people
(
    id        serial primary key,
    device_id int references devices (id),
    people_id int references people (id)
);

insert into devices(name, price)
values ('Smartphone', 10000.00),
       ('Laptop', 15000.00),
       ('Tablet', 8000.00),
       ('Smartwatch', 12000.00),
       ('Desktop', 20000.00),
       ('Printer', 5000.00),
       ('Monitor', 7000.00),
       ('Keyboard', 6000.00),
       ('Mouse', 3000.00),
       ('Router', 10000.00);

insert into people(name)
values ('John Doe'),
       ('Jane Smith'),
       ('Alice Johnson'),
       ('Robert Brown'),
       ('Emily Davis'),
       ('Michael Wilson'),
       ('Sarah Miller'),
       ('David Anderson'),
       ('Jessica Lee'),
       ('Daniel Harris');


insert into devices_people(device_id, people_id)
values (1, 1),
       (2, 1),
       (3, 2),
       (4, 3),
       (5, 3),
       (1, 4),
       (2, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10),
       (1, 2),
       (2, 3),
       (3, 4),
       (4, 5),
       (5, 6),
       (6, 7),
       (7, 8),
       (8, 9),
       (9, 10);


-- 3. Используя агрегатные функции вывести среднюю цену устройств.
select avg(price) as avg_price
from devices;

-- 4. Используя группировку вывести для каждого человека среднюю цену его устройств.
select p.name, avg(price) as device_price
from devices
         join devices_people on devices.id = devices_people.device_id
         join people p on p.id = devices_people.people_id
group by p.name;

-- 5. Дополнить запрос п.4. условием, что средняя стоимость устройств должна быть больше 5000.

select p.name, avg(price) as device_price
from devices
         join devices_people on devices.id = devices_people.device_id
         join people p on p.id = devices_people.people_id
group by p.name
having avg(price) > 5000;

-- сброс данных таблицы devices
-- truncate table devices restart identity cascade;
