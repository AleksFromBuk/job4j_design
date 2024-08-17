create table car_bodies
(
    id   serial primary key,
    name varchar(128)
);

create table car_engines
(
    id   serial primary key,
    name varchar(128)
);

create table car_transmissions
(
    id   serial primary key,
    name varchar(128)
);

create table cars
(
    id              serial primary key,
    name            varchar(128),
    body_id         int references car_bodies (id),
    engine_id       int references car_engines (id),
    transmission_id int references car_transmissions (id)
);

-- data insert
INSERT INTO car_bodies (name) VALUES
                                  ('�����'),
                                  ('�������'),
                                  ('�����'),
                                  ('����'),
                                  ('���������');

INSERT INTO car_engines (name) VALUES
                                   ('V8'),
                                   ('V6'),
                                   ('����������������'),
                                   ('������'),
                                   ('��������������');

INSERT INTO car_transmissions (name) VALUES
                                         ('��������'),
                                         ('�������'),
                                         ('��������'),
                                         ('�����'),
                                         ('������');

INSERT INTO cars (name, body_id, engine_id, transmission_id) VALUES
                                                                 ('Ford Mustang', 1, 1, 2),        -- ����� � V8 � ���������
                                                                 ('Toyota Prius', 2, 3, 3),        -- ������� � ����������������� � ����������
                                                                 ('Chevrolet Silverado', 3, 4, 1), -- ����� � ������� � ���������
                                                                 ('Tesla Model S', 1, 3, 4),       -- ����� � ����������������� � �������
                                                                 ('Mazda RX-8', 4, 2, 5),          -- ���� � V6 � ������ ��������
                                                                 ('BMW X5', 5, 5, NULL),           -- ��������� � ���������������, �� ��� ������� �������
                                                                 ('Nissan Leaf', 2, NULL, 3),      -- ������� ��� ���������, �� � ����������
                                                                 ('Hyundai Elantra', 1, NULL, NULL); -- ����� ��� ��������� � ������� �������

-- ������� ������ ���� ����� � ��� ����������� � ��� ������.
-- ����� ������, ��� �����-�� ������� ������ ����� � �� ���������.
-- � ����� ������ �������� ����� ���� null ��� ������ (��������, �������� ��������� null);
--
-- ������ "�����" ��� ������:
-- id, car_name, body_name, engine_name, transmission_name

select c.name, cb.name, ce.name, ct.name from cars c
left join car_bodies cb on cb.id = c.body_id
left join car_engines ce on c.engine_id = ce.id
left join car_transmissions ct on c.transmission_id = ct.id;

-- ������� ������, ������� �� ������������ �� � ����� ������.
-- "�� ������������" ������, ��� ����� ������� ������� cars �����������
-- ������� �����, ��������� �� ������� car_bodies. ��������, �� ��������
-- � car_bodies "�����", "�������" � "�����", � ��� ���������� � ������� cars
-- ������� ������ ������� ����� �� ������ "�����" � "�������". ������,
-- ���������� ����� ������, ������ ������� "�����", �.�. ����� ����� ��� ���,
-- ��� �������� ����� �������;

INSERT INTO car_bodies (name) VALUES ('������');
INSERT INTO car_bodies (name) VALUES ('��������');

select cb.name from car_bodies cb
left join cars c on cb.id = c.body_id
where c.id is null ;

-- ������� ���������, ������� �� ������������ �� � ����� ������, ���������� �.2;

INSERT INTO car_engines (name) VALUES ('��������� ���������');

select ce.name from car_engines ce
                        left join cars c on ce.id = c.engine_id
where c.id is null ;

-- ������� ������� �������, ������� �� ������������ �� � ����� ������, ���������� �.2.

INSERT INTO car_transmissions (name) VALUES ('�����������������');

select ct.name from car_transmissions ct
                        left join cars c on ct.id = c.transmission_id
where c.id is null ;