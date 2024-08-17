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
values ('СЫР'),
       ('МОЛОКО'),
       ('ЙОГУРТ'),
       ('МЯСО'),
       ('РЫБА');

insert into product (name, type_id, expired_date, price)
values ('Сыр Чеддер', 1, '2024-07-15', 200.00),
       ('Сыр Пармезан', 1, '2024-09-30', 220.00),
       ('Молоко цельное', 2, '2024-08-05', 50.00),
       ('Йогурт клубничный', 3, '2024-07-25', 30.00),
       ('Мороженое ванильное', 2, '2024-12-01', 100.00),
       ('Мороженое шоколадное', 2, '2024-12-01', 120.00),
       ('Сыр Моцарелла', 1, '2024-06-30', 180.00),
       ('Сыр Рикотта', 1, '2023-12-31', 150.00),
       ('Мясо говядина', 4, '2024-08-15', 300.00),
       ('Рыба лосось', 5, '2024-07-28', 350.00),
       ('Сыр плавленный', 1, '2024-08-10', 80.00),
       ('Молоко обезжиренное', 2, '2024-08-06', 55.00),
       ('Йогурт ванильный', 3, '2024-07-26', 35.00),
       ('Рыба тунец', 5, '2024-07-27', 320.00),
       ('Мясо свинина', 4, '2024-08-10', 290.00),
       ('Рыба свежий лещЪ', 5, '2024-07-28', 350.00),
       ('Йогурт натуральный', 3, '2024-07-27', 25.00);

--    1. Написать запрос получение всех продуктов с типом "СЫР"

-- не гибкий вариант
-- select * from product as pp
-- where pp.type_id = '1';

select p.name, p.type_id, p.expired_date, p.price
from product as p
         join type t on p.type_id = t.id and t.name = 'СЫР';

-- 2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженое"

select p.name, p.type_id, p.expired_date, p.price
from product as p
where p.name LIKE '%Мороженое%';

-- 3. Написать запрос, который выводит все продукты, срок годности которых уже истек
select p.name, p.type_id, p.expired_date, p.price
from product as p
where p.expired_date < current_date;


-- 4. Написать запрос, который выводит самый дорогой продукт.
--    Запрос должен быть универсальный и находить все продукты с
--    максимальной ценой. Например, если в таблице есть продукт
--    типа СЫР с ценой 200 и продукт типа МОЛОКО с ценой 200, и эта
--    цена максимальная во всей таблице, то запрос должен вывести оба этих продукта.
select p.name, p.type_id, p.expired_date, p.price
from product as p
where p.price = (select max(price) from product);

-- 5. Написать запрос, который выводит для каждого типа количество продуктов
-- к нему принадлежащих. В виде имя_типа, количество
select t.name, count(p.id)
from type as t
         join product as p on t.id = p.type_id
group by t.name;

-- 6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
select p.name, p.type_id, p.expired_date, p.price
from product as p
         join type t on t.id = p.type_id
where t.name IN ('СЫР', 'МОЛОКО')
order by p.type_id;

-- 7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.
-- Под количеством подразумевается количество продуктов определенного типа. Например,
-- если есть тип "СЫР" и продукты "Сыр плавленный" и "Сыр моцарелла", которые ему принадлежат,
-- то количество продуктов типа "СЫР" будет 2.
select t.name
from type as t
         join product p on t.id = p.type_id
group by t.name
having count(p.id) < 10;

-- 8. Вывести все продукты и их тип.
select p.name, t.name ТИП, p.expired_date, p.price
from product as p
         join type t on t.id = p.type_id;