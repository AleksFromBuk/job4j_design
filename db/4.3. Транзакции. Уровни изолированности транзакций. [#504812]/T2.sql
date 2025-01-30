-- **********************Read committed*************************
-- BEGIN;
-- SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
-- -- после внесени€ изменений в таблицу, произведенных
-- -- в рамках T1, проверим состо€ние в рамках текущей сессии
-- -- выполнить до шага T1.4 и после шага T1.4
--
-- --(“еперь “2 видит все, что сделала “1. Ёто так называемые феномен неповтор€ющегос€ чтени€,
-- -- когда мы видим обновленные и удаленные строки (UPDATE, DELETE), и феномен чтени€ фантомов,
-- -- когда мы видим добавленные записи (INSERT))
-- select * from accounts;
--
-- ROLLBACK;

--***************** Repeatable read**********************
-- шаг 1
-- BEGIN;
-- SET TRANSACTION ISOLATION LEVEL Repeatable read;
-- select * from accounts;

-- -- шаг 2
-- -- в “2 пытаемс€ обновить ту же самую строку, которую обновили в “1
-- -- и получим Lock из-за параллельной транзакции T1, котора€ затрагивает эту же строку
-- UPDATE accounts SET balance = 2000 WHERE login = 'vasya';


-- -- шаг 3
-- ROLLBACK;

-- --  шаг 4: удалим строки из таблицы и снова выполним инсерт исходных данных, и повторим шаги дл€ T1,
-- -- а также здесь - дл€ T2, за исключением апдейта в рамках шага 2.
-- select * from accounts;


-- **************Serializable******************
-- шаг 1
BEGIN;
SET TRANSACTION ISOLATION LEVEL serializable ;
-- шаг 2
select * from accounts;
-- шаг 3
-- ѕытаемс€ изменить баланс
UPDATE accounts SET balance = balance - 200 WHERE login = 'mark';
-- ќжидаем блокировку или ошибку сериализации
-- ROLLBACK ;
