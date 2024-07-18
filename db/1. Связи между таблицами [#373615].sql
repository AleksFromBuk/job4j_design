
create table Computer
(
    id          serial PRIMARY KEY,
    model       varchar(255) NOT NULL,
    device_name varchar(255) NOT NULL,
    ram         integer NOT NULL,
    UNIQUE (model),
    UNIQUE (device_name)
);

create table Network_interface_type
(
    id        serial primary key,
    type_name varchar(255) NOT NULL UNIQUE
);

create table Network_interface
(
    id             serial primary key,
    interface_name varchar(255) NOT NULL,
    mac_address    varchar(255) NOT NULL UNIQUE,
    type_id integer NOT NULL references Network_interface_type(id)
);

create table computer_network_interface
(
    computer_id          integer NOT NULL references Computer (id),
    network_interface_id integer NOT NULL references Network_interface (id),
    primary key (computer_id, network_interface_id)
);

create table user_PC
(
    id serial primary key,
    name varchar(128) NOT NULL,
    created_at timestamp NOT NULL,
    device integer NOT NULL references Computer(id) UNIQUE
);


INSERT INTO Network_interface_type (type_name) VALUES
                                                   ('Ethernet'),
                                                   ('WiFi'),
                                                   ('Bluetooth');


INSERT INTO Computer (model, device_name, ram) VALUES
                                                   ('Dell XPS 13', 'Laptop1', 16),
                                                   ('MacBook Pro', 'Laptop2', 32),
                                                   ('HP Spectre', 'Laptop3', 8);

INSERT INTO Network_interface (interface_name, mac_address, type_id) VALUES
                                                                         ('eth0', '00:1A:2B:3C:4D:5E', 1),
                                                                         ('wlan0', '00:1A:2B:3C:4D:5F', 2),
                                                                         ('bt0', '00:1A:2B:3C:4D:60', 3);


INSERT INTO computer_network_interface (computer_id, network_interface_id) VALUES
                                                                               (1, 1),
                                                                               (1, 2),
                                                                               (2, 2),
                                                                               (2, 3),
                                                                               (3, 1);


INSERT INTO User_PC (name, created_at, device) VALUES
                                                   ('Alice', '2023-01-01 10:00:00', 1),
                                                   ('Bob', '2023-02-01 11:00:00', 2),
                                                   ('Charlie', '2023-03-01 12:00:00', 3);
