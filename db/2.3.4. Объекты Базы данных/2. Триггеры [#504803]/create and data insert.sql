create table products
(
    id       serial primary key,
    name     varchar(50),
    producer varchar(50),
    count    integer default 0,
    price    integer
);


select *
from products;

-- **** триггер на row-уровне ****

create
    or replace function discount()
    returns trigger as
$$
BEGIN
    update products
    set price = price - price * 0.2
    where count <= 5
      AND id = new.id;
    return NEW;
END;
$$
    LANGUAGE 'plpgsql';

create trigger discount_trigger
    after insert
    on products
    for each row
execute procedure discount();

-- SELECT n.nspname as "Schema",
-- p.proname as "Function Name"
-- FROM pg_proc p
-- JOIN pg_namespace n ON p.pronamespace = n.oid
-- WHERE p.proname = 'discount';

select *
from products;


INSERT INTO products(name, producer, count, price)
VALUES ('product_1', 'producer_1', 8, 115);

insert into products (name, producer, count, price)
VALUES ('product_1', 'producer_1', 3, 50);

alter table products
    disable trigger discount_trigger;

insert into products (name, producer, count, price)
VALUES ('product_1', 'producer_1', 3, 50);

alter table products
    enable trigger discount_trigger;

insert into products (name, producer, count, price)
VALUES ('product_2', 'producer_2', 3, 50);


-- **** триггер на уровне statement ****

-- определение функции tax()
create
    or replace function tax()
    returns trigger as
$$
BEGIN
    update products
    set price = price * 0.8
    where id IN (select id from inserted) -- используем IN для множества строк
      and count <= 5;
    return NULL; -- statement-level trigger doesn't return a value
END
$$
    LANGUAGE 'plpgsql';


create trigger tax_trigger
    after insert
    on products
    referencing new table as
        inserted
    for each statement
execute procedure tax();

-- 1)  Триггер должен срабатывать после вставки данных,
-- для любого товара и просто насчитывать налог на товар
-- (нужно прибавить налог к цене товара). Действовать он должен
-- не на каждый ряд, а на запрос (statement уровень)

-- функция, которая начисляет налог 10% от исходной стоимости
create
    or replace function product_tax()
    returns trigger as
$$
BEGIN
    update products
    set price = price * 1.1
    where id IN (select id from products);
    return NULL; -- На уровне statement мы не возвращаем значение
END
$$
    LANGUAGE 'plpgsql';

-- Создание триггера на уровне statement после вставки данных
create trigger product_tax_trigger
    after insert
    on products
    for each statement
execute procedure product_tax();

-- alter table products
--     disable trigger discount_trigger;
-- alter table products
--     disable trigger tax_trigger;

-- проверка триггера начисления налога
insert into products (name, producer, count, price)
VALUES ('UFO', 'Micro Prose', 3, 250);


--2) Триггер должен срабатывать до вставки данных и насчитывать налог на товар
-- (нужно прибавить налог к цене товара). Здесь используем row уровень.

alter table products
    disable trigger product_tax_trigger;

-- функция, которая начисляет налог 10% от исходной стоимости row-уровень, поэтому используется функция,
-- возвращающая не NULL
create
    or replace function row_level_product_tax()
    returns trigger as
$$
BEGIN
    new.price := new.price * 1.1;
    return new;
END
$$
    LANGUAGE 'plpgsql';

-- Создание триггера на уровне statement до вставки данных
create trigger product_tax_trigger_before_insert
    before insert
    on products
    for each row
execute procedure row_level_product_tax();

insert into products (name, producer, count, price)
VALUES ('Doom 2', 'id Software', 3, 250);

-- 3) Нужно написать триггер на row уровне,
-- который сразу после вставки продукта в таблицу
-- products, будет заносить имя, цену и текущую дату
-- в таблицу history_of_price.

create table history_of_price
(
    id    serial primary key,
    name  varchar(50),
    price integer,
    date  timestamp
);

-- используемая функция для триггера
create
    or replace function log_product_insertion()
    returns trigger as
$$
BEGIN
    insert into history_of_price (name, price, date)
    values (new.name, new.price, current_timestamp);
    return new;
END
$$
    LANGUAGE 'plpgsql';

-- Создание триггера на row-уровне после вставки данных в таблицу
-- product
create trigger log_after_insert_in_products
    after insert
    on products
    for each row
execute procedure log_product_insertion();

-- вставка данных в products для проверки работы триггера log_after_insert_in_products
insert into products (name, producer, count, price)
values ('Br. Mario', 'Nintendo', 3, 250);

-- show triggers
SELECT tg.tgname   AS trigger_name,
       rel.relname AS table_name,
       nsp.nspname AS schema_name
FROM pg_trigger tg
         JOIN
     pg_class rel ON tg.tgrelid = rel.oid
         JOIN
     pg_namespace nsp ON rel.relnamespace = nsp.oid
WHERE nsp.nspname = current_schema()
  AND NOT tg.tgisinternal;
--

-- show enabled/disabled
SELECT tg.tgname   AS trigger_name,
       rel.relname AS table_name,
       nsp.nspname AS schema_name,
       CASE
           WHEN tg.tgenabled = 'O' THEN 'ENABLED'
           ELSE 'DISABLED'
           END     AS trigger_status
FROM pg_trigger tg
         JOIN
     pg_class rel ON tg.tgrelid = rel.oid
         JOIN
     pg_namespace nsp ON rel.relnamespace = nsp.oid
WHERE nsp.nspname = current_schema()
  AND NOT tg.tgisinternal
  AND tg.tgenabled = 'O';
--
