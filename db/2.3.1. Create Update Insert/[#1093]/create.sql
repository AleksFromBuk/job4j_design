create TYPE state_type as enum ('NEW', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED');

create table roles
(
    id        serial primary key,
    role_name text not null
);

create table users
(
    id      serial primary key,
    name    text not null,
    role_id int references roles (id)
);



create table rules
(
    id        serial primary key,
    rule_name text not null
);

create table role_rules
(
    role_id int references roles (id),
    rule_id int references rules (id),
    primary key (role_id, rule_id)
);

create table states
(
    id          serial primary key,
    name        varchar(16),
    description varchar(256),
    state_enum  state_type,
    actual      boolean NOT NULL default true
);

create table categories (
                            id serial primary key,
                            name varchar(64)
);

create table items
(
    id          serial primary key,
    name        varchar(64) not null,
    description text        not null,
    user_id     int references users (id),
    category_id int references categories (id),
    state_id    int references states (id),
    created_at  timestamp default current_timestamp
);

create table comments
(
    id           serial primary key,
    comment_text text not null,
    item_id      int references items (id),
    create_at timestamp default current_timestamp
);

create table attachs
(
    id        serial primary key,
    file_path varchar(256) not null,
    item_id   int references items (id),
    uploaded_at timestamp default current_timestamp
);
