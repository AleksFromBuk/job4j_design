create table products
(
    id       serial primary key,
    name     varchar(50),
    producer varchar(50),
    count    integer default 0,
    price    integer
);

-- ***********task*********************

-- �������� ����������
BEGIN TRANSACTION;

-- ��������� ����� ������ � ������� products
INSERT INTO products (name, producer, count, price) VALUES ('product_A', 'producer_A', 20, 150);
INSERT INTO products (name, producer, count, price) VALUES ('product_B', 'producer_B', 10, 100);

-- ������������� ������ ����� ����������
SAVEPOINT first_savepoint;

-- ������� ���� ������
DELETE FROM products WHERE name = 'product_A';

-- ��������� ���� ��� ����� �� �������
UPDATE products SET price = 200 WHERE name = 'product_B';

-- ������������� ������ ����� ����������
SAVEPOINT second_savepoint;

-- ��������� ����� ������
INSERT INTO products (name, producer, count, price) VALUES ('product_C', 'producer_C', 5, 300);

-- ��������� ������� ������
SELECT * FROM products;

-- ���������� ��������� �� ������ ����� ���������� (�������� DELETE � UPDATE)
ROLLBACK TO first_savepoint;

-- ��������� ������ ����� ������
SELECT * FROM products;

-- ������������ ��������� ���������
COMMIT TRANSACTION;

-- ��������� ��������� ��������� �������
SELECT * FROM products;
