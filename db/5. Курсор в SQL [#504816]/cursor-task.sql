insert into products (name, count, price)
VALUES ('product_1', 1, 5);
insert into products (name, count, price)
VALUES ('product_2', 2, 10);
insert into products (name, count, price)
VALUES ('product_3', 3, 15);
insert into products (name, count, price)
VALUES ('product_4', 4, 20);
insert into products (name, count, price)
VALUES ('product_5', 5, 25);
insert into products (name, count, price)
VALUES ('product_6', 6, 30);
insert into products (name, count, price)
VALUES ('product_7', 7, 35);
insert into products (name, count, price)
VALUES ('product_8', 8, 40);
insert into products (name, count, price)
VALUES ('product_9', 9, 45);
insert into products (name, count, price)
VALUES ('product_10', 10, 50);
insert into products (name, count, price)
VALUES ('product_11', 11, 55);
insert into products (name, count, price)
VALUES ('product_12', 12, 60);
insert into products (name, count, price)
VALUES ('product_13', 13, 65);
insert into products (name, count, price)
VALUES ('product_14', 14, 70);
insert into products (name, count, price)
VALUES ('product_15', 15, 75);
insert into products (name, count, price)
VALUES ('product_16', 16, 80);
insert into products (name, count, price)
VALUES ('product_17', 17, 85);
insert into products (name, count, price)
VALUES ('product_18', 18, 90);
insert into products (name, count, price)
VALUES ('product_19', 19, 95);
insert into products (name, count, price)
VALUES ('product_20', 20, 100);

-- шаг 1. Создадим курсор с возможностью перемещения (SCROLL),
-- чтобы мы могли двигаться как вперед, так и назад.
BEGIN;

DECLARE cursor_products SCROLL CURSOR FOR
    SELECT * FROM products ORDER BY id;

-- шаг 2. Переход на последнюю запись
-- Передвигаем курсор в конец таблицы (на запись с id = 20):
FETCH LAST FROM cursor_products;

-- шаг 3. Теперь от последней записи переходим к записи
-- с id = 15 (назад на 5 позиций):
MOVE BACKWARD 6 FROM cursor_products;
FETCH NEXT FROM cursor_products;

-- шаг 4. Теперь переходим с 15-й на 7-ю запись (назад на 8 позиций):
MOVE BACKWARD 9 FROM cursor_products;
FETCH NEXT FROM cursor_products;

-- шаг 5. Теперь передвигаемся с 7-й на 2-ю запись (назад на 5 позиций):
MOVE BACKWARD 6 FROM cursor_products;
FETCH NEXT FROM cursor_products;

-- шаг 6. Теперь завершаем проход, перейдя к 1-й записи:
MOVE BACKWARD 2 FROM cursor_products;
FETCH NEXT FROM cursor_products;

-- шаг 7.
CLOSE cursor_products;
COMMIT;
