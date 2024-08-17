create table students
(
    id   serial primary key,
    name varchar(50)
);

insert into students (name)
values ('���� ������'),
       ('���� ������');

create table authors
(
    id   serial primary key,
    name varchar(50)
);

insert into authors (name)
values ('��������� ������'),
       ('������� ������');

create table books
(
    id        serial primary key,
    name      varchar(200),
    author_id integer references authors (id)
);

insert into books (name, author_id)
values ('������� ������', 1),
       ('����������� �����', 1),
       ('����������', 1),
       ('̸����� ����', 2),
       ('���', 2);

create table orders
(
    id         serial primary key,
    active     boolean default true,
    book_id    integer references books (id),
    student_id integer references students (id)
);

insert into orders (book_id, student_id)
values (1, 1),
       (3, 1),
       (5, 2),
       (4, 1),
       (2, 2);

-- queries

-- ������ �������� ������, � ������� �� ��������� ������ ���������,
-- � ������� ��������� 2 � ����� ���� ������ � ���� �� ������. ��� ����
-- � �������������� ������� ������ ���� �������� ��� ��������, ���������� ����,
-- ��� ������. ���� ����������� ������, ������� �������� �������� ��������� ������:

-- select s.name, count(o.id), a.name
-- from orders o
--          left join students s on o.student_id = s.id
--          left join books b on b.id = o.book_id
--          left join authors a on b.author_id = a.id
-- group by (s.name, a.name)
-- having count(o.id) >= 2;

create view show_students_with_2_or_more_books
as
select s.name as student, count(o.id), a.name as author
from orders o
         left join students s on o.student_id = s.id
         left join books b on b.id = o.book_id
         left join authors a on b.author_id = a.id
group by (s.name, a.name)
having count(o.id) >= 2;

select * from show_students_with_2_or_more_books;

create view show_students_with_2_or_more_books_with_book_name
as
select s.name as student, a.name as author, b.name as book
from orders o
         left join students s on o.student_id = s.id
         left join books b on b.id = o.book_id
         left join authors a on b.author_id = a.id
where (s.id, a.id) in (select s.id, a.id
                       from orders o
                                left join students s on o.student_id = s.id
                                left join books b on b.id = o.book_id
                                left join authors a on b.author_id = a.id
                       group by s.id, a.id
                       having count(o.id) >= 2);

select * from show_students_with_2_or_more_books_with_book_name;

