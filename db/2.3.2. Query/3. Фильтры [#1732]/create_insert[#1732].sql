create table type
(
    id   serial primary key,
    name varchar(255)
);

create table product
(
    id           serial primary key,
    name         varchar(255),
    type_id      int references type (id),
    expired_date date,
    price        float
);

insert into type (name)
values ('���'),
       ('������'),
       ('������'),
       ('����'),
       ('����');

insert into product (name, type_id, expired_date, price)
values ('��� ������', 1, '2024-07-15', 200.00),
       ('��� ��������', 1, '2024-09-30', 220.00),
       ('������ �������', 2, '2024-08-05', 50.00),
       ('������ ����������', 3, '2024-07-25', 30.00),
       ('��������� ���������', 2, '2024-12-01', 100.00),
       ('��������� ����������', 2, '2024-12-01', 120.00),
       ('��� ���������', 1, '2024-06-30', 180.00),
       ('��� �������', 1, '2023-12-31', 150.00),
       ('���� ��������', 4, '2024-08-15', 300.00),
       ('���� ������', 5, '2024-07-28', 350.00),
       ('��� ����������', 1, '2024-08-10', 80.00),
       ('������ ������������', 2, '2024-08-06', 55.00),
       ('������ ���������', 3, '2024-07-26', 35.00),
       ('���� �����', 5, '2024-07-27', 320.00),
       ('���� �������', 4, '2024-08-10', 290.00),
       ('���� ������ ����', 5, '2024-07-28', 350.00),
       ('������ �����������', 3, '2024-07-27', 25.00);

--    1. �������� ������ ��������� ���� ��������� � ����� "���"

-- �� ������ �������
-- select * from product as pp
-- where pp.type_id = '1';

select p.name, p.type_id, p.expired_date, p.price
from product as p
         join type t on p.type_id = t.id and t.name = '���';

-- 2. �������� ������ ��������� ���� ���������, � ���� � ����� ���� ����� "���������"

select p.name, p.type_id, p.expired_date, p.price
from product as p
where p.name LIKE '%���������%';

-- 3. �������� ������, ������� ������� ��� ��������, ���� �������� ������� ��� �����
select p.name, p.type_id, p.expired_date, p.price
from product as p
where p.expired_date < current_date;


-- 4. �������� ������, ������� ������� ����� ������� �������.
--    ������ ������ ���� ������������� � �������� ��� �������� �
--    ������������ �����. ��������, ���� � ������� ���� �������
--    ���� ��� � ����� 200 � ������� ���� ������ � ����� 200, � ���
--    ���� ������������ �� ���� �������, �� ������ ������ ������� ��� ���� ��������.
select p.name, p.type_id, p.expired_date, p.price
from product as p
where p.price = (select max(price) from product);

-- 5. �������� ������, ������� ������� ��� ������� ���� ���������� ���������
-- � ���� �������������. � ���� ���_����, ����������
select t.name, count(p.id)
from type as t
         join product as p on t.id = p.type_id
group by t.name;

-- 6. �������� ������ ��������� ���� ��������� � ����� "���" � "������"
select p.name, p.type_id, p.expired_date, p.price
from product as p
         join type t on t.id = p.type_id
where t.name IN ('���', '������')
order by p.type_id;

-- 7. �������� ������, ������� ������� ��� ���������, ������� �������� ������ 10 ����.
-- ��� ����������� ��������������� ���������� ��������� ������������� ����. ��������,
-- ���� ���� ��� "���" � �������� "��� ����������" � "��� ���������", ������� ��� �����������,
-- �� ���������� ��������� ���� "���" ����� 2.
select t.name
from type as t
         join product p on t.id = p.type_id
group by t.name
having count(p.id) < 10;

-- 8. ������� ��� �������� � �� ���.
select p.name, t.name ���, p.expired_date, p.price
from product as p
         join type t on t.id = p.type_id;