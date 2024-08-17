create table owners
(
    id   serial primary key,
    name varchar(255)
);

create table devices
(
    id       serial primary key,
    name     varchar(255),
    owner_id int references owners (id)
);

insert into owners(name)
values ('Owner 1'),
       ('Owner 2'),
       ('Owner 3');

insert into devices(name, owner_id)
values ('Device 1', 1),
       ('Device 2', 2),
       ('Device 3', 3),
       ('Device 4', null),
       ('Device 5', null),
       ('Device 6', 1);

-- left join
select *
from devices d
         left join owners O on d.owner_id = O.id;

-- "Информативность данного запроса заключается в том,
-- что мы можем получить устройства, у которых нет владельца.
-- Для этого достаточно дополнить запрос фильтром."
select *
from devices d
         left join owners O on d.owner_id = O.id
where O.id is null;

-- в результате получиться число строк, которое больше,
-- чем количество строк в таблице Owner
select *
from owners o
         left join devices d on o.id = d.owner_id;

-- right outer join
--Следовательно, следующие пары запросов будут работать
-- одинаково, отличаться будет возможно только порядок
-- столбцов в результирующей выборке.
select *
from devices d
         left join owners o on d.owner_id = o.id;
select *
from owners o
         right join devices d on d.owner_id = o.id;

select *
from owners o
         left join devices d on o.id = d.owner_id;
select *
from devices d
         right join owners o on d.owner_id = o.id;

-- full join
--Данный тип внешнего соединения дает результат левого + правого соединений, т.е. представляет собой комбинацию этих двух соединений

select *
from devices d
         full join owners o on d.owner_id = o.id;

-- cross join
--Результатом этого запроса является декартово множество, т.е. все пары элементов.
select *
from devices d
         cross join owners o;

-- пример использования cross join

create table numbers
(
    num int unique
);

insert into numbers(num)
values (1),
       (2),
       (3);

select n1.num as a, n2.num as b, (n1.num * n2.num) as "a * b = "
from numbers n1
         cross join numbers n2;

-- task

create table departments
(
    id   serial primary key,
    name varchar(255)
);

create table employees
(
    id   serial primary key,
    name varchar(255),
    depart_id int references departments(id)
)

CREATE TABLE teens (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       gender CHAR(1) CHECK (gender IN ('M', 'F'))
);
-- data insert
INSERT INTO departments (name) VALUES
                                   ('HR'),
                                   ('IT'),
                                   ('Finance'),
                                   ('Marketing'),
                                   ('Sales');

INSERT INTO employees (name, depart_id) VALUES
                                                ('Alice', 1),
                                                ('Bob', 2),
                                                ('Charlie', 2),
                                                ('David', 3),
                                                ('Eve', 5);

INSERT INTO teens (name, gender) VALUES
                                     ('Vasya', 'M'),
                                     ('Masha', 'F'),
                                     ('Ivan', 'M'),
                                     ('Anna', 'F'),
                                     ('Petr', 'M'),
                                     ('Olga', 'F');

-- 2. Выполнить запросы с left, right, full, cross соединениями

select * from departments d left join employees e on d.id = e.depart_id;
select * from employees e left join departments d on e.depart_id = d.id;

select * from departments d right join employees e on d.id = e.depart_id;
select * from employees e right join departments d  on d.id = e.depart_id;

select * from departments d full join employees e on d.id = e.depart_id;
select * from employees e full join departments d on e.depart_id = d.id;

select * from departments d cross join employees e;

-- 3. Используя left join найти департаменты, у которых нет работников

select * from departments d left join employees e on d.id = e.depart_id
where e.depart_id is NULL;

-- 4. Используя left и right join написать запросы,
-- которые давали бы одинаковый результат (порядок вывода колонок в эти запросах также должен быть идентичный).
select e.id, e.name, e.depart_id, d.id, d.name  from departments d  left join employees e on d.id = e.depart_id;
select e.id, e.name, e.depart_id, d.id, d.name from employees e right join departments d  on d.id = e.depart_id;

-- 5. Создать таблицу teens с атрибутами name, gender и заполнить ее. Используя cross join составить все возможные
-- разнополые пары. Исключите дублирование пар вида Вася-Маша и Маша-Вася.

select * from teens a cross join teens b  where a.gender <> b.gender
and a.name < b.name;