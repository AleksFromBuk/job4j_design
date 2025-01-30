-- **********************Read committed*************************
-- шаг 1
-- BEGIN;
-- SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
-- -- шаг 2
-- select * from accounts;
--
-- -- шаг 3
-- -- Производим изменения в таблице в рамках T1
-- INSERT INTO accounts (login, balance, created_at) VALUES ('Illya', 750, now());
-- DELETE FROM accounts WHERE login = 'Vasya';
-- UPDATE accounts SET balance = 1500 WHERE login = 'Petya';
--
-- -- по желанию
-- select * from accounts;


-- -- шаг 4
-- COMMIT;
-- ROLLBACK;

--***************** Repeatable read**********************

-- шаг 1
-- BEGIN;
-- SET TRANSACTION ISOLATION LEVEL Repeatable read;
-- -- шаг 2
-- select * from accounts;
-- -- шаг 3
-- -- Производим изменения в таблице в рамках T1
-- INSERT INTO accounts (login, balance, created_at) VALUES ('Illya', 750, now());
-- UPDATE accounts SET balance = 1500 WHERE login = 'vasya';
-- DELETE FROM accounts WHERE login = 'petya';
-- -- шаг 4 - проверяем результат шага 3
-- select * from accounts;
--
-- Шаг 4. сделаем ROLLBACK в T2 и затем зафиксируем изменения, которые сделала Т1.
-- И прочитаем снова данные из таблицы accounts в Т2.
-- COMMIT ;

-- по желанию
-- select * from accounts;

-- после COMMIT на шаге 4, проверим в T2 состояние открытой в сессии транзакции -->


-- **************Serializable******************
-- шаг 1
BEGIN;
SET TRANSACTION ISOLATION LEVEL serializable ;
-- шаг 2
select * from accounts;
-- шаг 3
SELECT * FROM accounts WHERE login = 'mark';
-- Увеличиваем баланс
UPDATE accounts SET balance = balance + 500 WHERE login = 'mark';
SELECT * FROM accounts WHERE login = 'mark';
-- Не выполняем COMMIT
COMMIT ;
select * from accounts;
