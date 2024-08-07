CREATE TABLE fauna
(
    id             SERIAL PRIMARY KEY,
    name           TEXT,
    avg_age        INT,
    discovery_date DATE
);

INSERT INTO fauna (name, avg_age, discovery_date)
VALUES ('Catfish', 3000, '1800-01-01'),   -- �������� 'fish', ���� �������� ������ 1950 ����
       ('Goldfish', 1500, '1850-07-15'),  -- �������� 'fish', ���� �������� ������ 1950 ����
       ('Zebrafish', 5000, '1955-04-01'), -- �������� 'fish'
       ('Tuna', 18000,
        '1920-08-20'),                    -- ����. ����������������� ����� � ��������� 10 000 � 21 000, ���� �������� ������ 1950 ����
       ('Elephant', 25000, '1900-03-25'), -- ���� �������� ������ 1950 ����
       ('Blue whale', 22000, NULL),       -- ����. ����������������� ����� ����� 21 000, ���� �������� ���������� (null)
       ('Salmon', 4000, '2000-05-01'),    -- ����������� ������
       ('Cavefish', 6000, '1980-09-10'),  -- �������� 'fish'
       ('Coelacanth', 20000,
        '1938-12-23'),                    -- ����. ����������������� ����� � ��������� 10 000 � 21 000, ���� �������� ������ 1950 ����
       ('Axolotl', 15000,
        NULL); -- ����. ����������������� ����� � ��������� 10 000 � 21 000, ���� �������� ���������� (null)

-- 1) ���������� ������, � ������� ��� name �������� ��������� fish
select * from fauna where name like '%fish%';

-- 2) ���������� ������, � ������� ����. ����������������� ����� ��������� � ��������� 10 000 � 21 000
select * from fauna where avg_age >= 10000 and avg_age <= 21000;

-- 3) ���������� ������, � ������� ���� �������� �� �������� (null)
select * from fauna where fauna.discovery_date is null;
-- 4) ���������� ������ �����, � ������� ���� �������� ������ 1950 ����
select * from fauna where discovery_date < '1950-01-01';
