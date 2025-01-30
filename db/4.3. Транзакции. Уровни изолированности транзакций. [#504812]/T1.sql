-- **********************Read committed*************************
-- ��� 1
-- BEGIN;
-- SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
-- -- ��� 2
-- select * from accounts;
--
-- -- ��� 3
-- -- ���������� ��������� � ������� � ������ T1
-- INSERT INTO accounts (login, balance, created_at) VALUES ('Illya', 750, now());
-- DELETE FROM accounts WHERE login = 'Vasya';
-- UPDATE accounts SET balance = 1500 WHERE login = 'Petya';
--
-- -- �� �������
-- select * from accounts;


-- -- ��� 4
-- COMMIT;
-- ROLLBACK;

--***************** Repeatable read**********************

-- ��� 1
-- BEGIN;
-- SET TRANSACTION ISOLATION LEVEL Repeatable read;
-- -- ��� 2
-- select * from accounts;
-- -- ��� 3
-- -- ���������� ��������� � ������� � ������ T1
-- INSERT INTO accounts (login, balance, created_at) VALUES ('Illya', 750, now());
-- UPDATE accounts SET balance = 1500 WHERE login = 'vasya';
-- DELETE FROM accounts WHERE login = 'petya';
-- -- ��� 4 - ��������� ��������� ���� 3
-- select * from accounts;
--
-- ��� 4. ������� ROLLBACK � T2 � ����� ����������� ���������, ������� ������� �1.
-- � ��������� ����� ������ �� ������� accounts � �2.
-- COMMIT ;

-- �� �������
-- select * from accounts;

-- ����� COMMIT �� ���� 4, �������� � T2 ��������� �������� � ������ ���������� -->


-- **************Serializable******************
-- ��� 1
BEGIN;
SET TRANSACTION ISOLATION LEVEL serializable ;
-- ��� 2
select * from accounts;
-- ��� 3
SELECT * FROM accounts WHERE login = 'mark';
-- ����������� ������
UPDATE accounts SET balance = balance + 500 WHERE login = 'mark';
SELECT * FROM accounts WHERE login = 'mark';
-- �� ��������� COMMIT
COMMIT ;
select * from accounts;
