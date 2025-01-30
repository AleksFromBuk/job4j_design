create table products
(
    id       serial primary key,
    name     varchar(50),
    producer varchar(50),
    count    integer default 0,
    price    integer
);

-- ***********task*********************

-- Начинаем транзакцию
BEGIN TRANSACTION;

-- Добавляем новые записи в таблицу products
INSERT INTO products (name, producer, count, price) VALUES ('product_A', 'producer_A', 20, 150);
INSERT INTO products (name, producer, count, price) VALUES ('product_B', 'producer_B', 10, 100);

-- Устанавливаем первую точку сохранения
SAVEPOINT first_savepoint;

-- Удаляем одну запись
DELETE FROM products WHERE name = 'product_A';

-- Обновляем цену для одной из записей
UPDATE products SET price = 200 WHERE name = 'product_B';

-- Устанавливаем вторую точку сохранения
SAVEPOINT second_savepoint;

-- Вставляем новую запись
INSERT INTO products (name, producer, count, price) VALUES ('product_C', 'producer_C', 5, 300);

-- Проверяем текущие данные
SELECT * FROM products;

-- Откатываем изменения до первой точки сохранения (отменяем DELETE и UPDATE)
ROLLBACK TO first_savepoint;

-- Проверяем данные после отката
SELECT * FROM products;

-- Окончательно фиксируем изменения
COMMIT TRANSACTION;

-- Проверяем финальное состояние таблицы
SELECT * FROM products;
