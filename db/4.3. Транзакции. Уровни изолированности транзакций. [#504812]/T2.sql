-- **********************Read committed*************************
-- BEGIN;
-- SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
-- -- ����� �������� ��������� � �������, �������������
-- -- � ������ T1, �������� ��������� � ������ ������� ������
-- -- ��������� �� ���� T1.4 � ����� ���� T1.4
--
-- --(������ �2 ����� ���, ��� ������� �1. ��� ��� ���������� ������� ���������������� ������,
-- -- ����� �� ����� ����������� � ��������� ������ (UPDATE, DELETE), � ������� ������ ��������,
-- -- ����� �� ����� ����������� ������ (INSERT))
-- select * from accounts;
--
-- ROLLBACK;

--***************** Repeatable read**********************
-- ��� 1
-- BEGIN;
-- SET TRANSACTION ISOLATION LEVEL Repeatable read;
-- select * from accounts;

-- -- ��� 2
-- -- � �2 �������� �������� �� �� ����� ������, ������� �������� � �1
-- -- � ������� Lock ��-�� ������������ ���������� T1, ������� ����������� ��� �� ������
-- UPDATE accounts SET balance = 2000 WHERE login = 'vasya';


-- -- ��� 3
-- ROLLBACK;

-- --  ��� 4: ������ ������ �� ������� � ����� �������� ������ �������� ������, � �������� ���� ��� T1,
-- -- � ����� ����� - ��� T2, �� ����������� ������� � ������ ���� 2.
-- select * from accounts;


-- **************Serializable******************
-- ��� 1
BEGIN;
SET TRANSACTION ISOLATION LEVEL serializable ;
-- ��� 2
select * from accounts;
-- ��� 3
-- �������� �������� ������
UPDATE accounts SET balance = balance - 200 WHERE login = 'mark';
-- ������� ���������� ��� ������ ������������
-- ROLLBACK ;
