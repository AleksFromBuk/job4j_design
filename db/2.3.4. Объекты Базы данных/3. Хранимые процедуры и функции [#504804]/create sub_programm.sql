create
    or replace procedure insert_data(i_name varchar, prod varchar, i_count integer, i_price integer)
    language 'plpgsql'
as
$$
BEGIN
    insert into products (name, producer, count, price)
    values (i_name, prod, i_count, i_price);
END;
$$;

call insert_data('product_2', 'producer_2', 15, 32);

create
    or replace procedure update_data(u_count integer, tax float, u_id integer)
    language 'plpgsql'
as
$$
BEGIN
    if u_count > 0 then
        update products
        set count = count - u_count
        where id = u_id
          and count >= update_data.u_count;
    end if;
    if
        tax > 0 then
        update products
        set price = price + price * tax;
    end if;
END;
$$;

call update_data(10, 0, 1);

-- alter table products
--     disable trigger discount_trigger;
-- alter table products
--     disable trigger tax_trigger;

call insert_data('product_1', 'producer_1', 5, 10);

alter table products
    disable trigger discount_trigger;

alter table products
    disable trigger product_tax_trigger_before_insert;

--

call insert_data('product_1', 'producer_1', 3, 50);
call insert_data('product_3', 'producer_3', 8, 115);


call update_data(0, 0.2, 0);

alter procedure update_data(u_count integer, tax float, u_id integer) rename to update;

drop procedure update(u_count integer, tax float, u_id integer);

delete
from products;
ALTER SEQUENCE products_id_seq RESTART WITH 1;

-- хранимые функции
create or replace function f_insert_Data(i_name varchar, prod varchar, i_count integer, i_price integer)
    returns void
    language 'plpgsql'
as
$$
begin
    insert into products (name, producer, count, price)
    values (i_name, prod, i_count, i_price);
end;
$$;

select f_insert_data('product_1', 'producer_1', 25, 50);

--
create or replace function f_update_data(u_count integer, tax float, u_id integer)
    returns integer
    language 'plpgsql'
as
$$
declare
    result integer;
begin
    if u_count > 0 THEN
        update products
        set count = count - u_count
        where id = u_id
          and u_count <= products.count;
        select into result count
        from products
        where id = u_id;
    ELSE
        -- Если условий нет, возвращаем текущее количество товара
        SELECT INTO result count
        FROM products
        WHERE id = u_id;
        RAISE NOTICE 'Not enough products in stock or u_count is zero. Current count: %, Requested count: %', result, u_count;
    end if;
    if tax > 0 THEN
        update products
        set price = price + price * tax;
        select into result sum(price)
        from products;
    end if;
    return result;
end;
$$;

select f_update_data(10, 0, 1);

select f_insert_data('product_2', 'producer_2', 15, 32);
select f_insert_data('product_3', 'producer_3', 8, 115);

select f_update_data(0, 0.2, 0);


-- task: процедура удаления записи из таблицы по id
create
    or replace procedure deleteById_data(u_id integer)
    language 'plpgsql'
as
$$
BEGIN

    delete
    from products
    where u_id = id;
END;
$$;

select f_insert_data('product_1', 'producer_1', 25, 50);
call deleteById_data(4);

-- функция, удаляющая записи из таблицы, у которых значение поля count равно нулю
create or replace function f_delete_if_count_is_zero()
    returns integer
    language 'plpgsql'
as
$$
declare
    deleted_count integer;
begin
    delete
    from products
    where count = 0;
    get diagnostics deleted_count = row_count;
    return deleted_count;
end;
$$;

select f_insert_data('product_4', 'producer_4', 0, 32);
select f_insert_data('product_5', 'producer_5', 0, 115);

select f_delete_if_count_is_zero();

