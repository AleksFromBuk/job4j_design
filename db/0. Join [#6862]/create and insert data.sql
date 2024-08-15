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

-- "��������������� ������� ������� ����������� � ���,
-- ��� �� ����� �������� ����������, � ������� ��� ���������.
-- ��� ����� ���������� ��������� ������ ��������."
select *
from devices d
         left join owners O on d.owner_id = O.id
where O.id is null;

-- � ���������� ���������� ����� �����, ������� ������,
-- ��� ���������� ����� � ������� Owner
select *
from owners o
         left join devices d on o.id = d.owner_id;

-- right outer join
--�������������, ��������� ���� �������� ����� ��������
-- ���������, ���������� ����� �������� ������ �������
-- �������� � �������������� �������.
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
--������ ��� �������� ���������� ���� ��������� ������ + ������� ����������, �.�. ������������ ����� ���������� ���� ���� ����������

select *
from devices d
         full join owners o on d.owner_id = o.id;

-- cross join
--����������� ����� ������� �������� ��������� ���������, �.�. ��� ���� ���������.
select *
from devices d
         cross join owners o;

-- ������ ������������� cross join

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

-- 2. ��������� ������� � left, right, full, cross ������������

select * from departments d left join employees e on d.id = e.depart_id;
select * from employees e left join departments d on e.depart_id = d.id;

select * from departments d right join employees e on d.id = e.depart_id;
select * from employees e right join departments d  on d.id = e.depart_id;

select * from departments d full join employees e on d.id = e.depart_id;
select * from employees e full join departments d on e.depart_id = d.id;

select * from departments d cross join employees e;

-- 3. ��������� left join ����� ������������, � ������� ��� ����������

select * from departments d left join employees e on d.id = e.depart_id
where e.depart_id is NULL;

-- 4. ��������� left � right join �������� �������,
-- ������� ������ �� ���������� ��������� (������� ������ ������� � ��� �������� ����� ������ ���� ����������).
select e.id, e.name, e.depart_id, d.id, d.name  from departments d  left join employees e on d.id = e.depart_id;
select e.id, e.name, e.depart_id, d.id, d.name from employees e right join departments d  on d.id = e.depart_id;

-- 5. ������� ������� teens � ���������� name, gender � ��������� ��. ��������� cross join ��������� ��� ���������
-- ���������� ����. ��������� ������������ ��� ���� ����-���� � ����-����.

select * from teens a cross join teens b  where a.gender <> b.gender
and a.name < b.name;