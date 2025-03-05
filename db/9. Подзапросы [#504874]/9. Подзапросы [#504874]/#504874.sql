CREATE TABLE IF NOT EXISTS customers
(
    id         serial primary key,
    first_name text,
    last_name  text,
    age        int,
    country    text
);

INSERT INTO customers (first_name, last_name, age, country)
VALUES ('John', 'Doe', 42, 'USA'),
       ('Chris', 'Rea', 52, 'USA'),
       ('Tony', 'Joe White', 52, 'USA'),
       ('Eric', 'Clapton', 59, 'USA'),
       ('Ozzy', 'Osbourne', 61, 'UK'),
       ('Ronnie James', 'Dio', 68, 'USA'),
       ('B.B.', 'King', 90, 'USA');

select *
from customers
where customers.age = (select min(customers.age)
                       from customers);

create table if not exists orders
(
    id          serial primary key,
    amount      int,
    customer_id int references customers (id)
);

insert into orders (amount, customer_id)
values (100, 1),
       (500, 1),
       (700, 1),
       (765, 3),
       (970, 2),
       (1700, 3),
       (1100, 4),
       (1200, 7);

select * from customers
where customers.id not in (
    select orders.customer_id from orders
    );

select c.* from customers c
left join orders on c.id = orders.customer_id
where orders.customer_id is null;
