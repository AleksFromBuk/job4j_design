create table aiport(
                       code char(3) primary key,
                       country varchar(128) NOT NULL,
                       city varchar(128) NOT NULL
);

select * from aiport;

delete from aiport;

create table airport(
                        code char(3) primary key,
                        country varchar(128) NOT NULL,
                        city varchar(128) NOT NULL,
                        erea int not null
)

select * from airport;

alter table airport
    rename column erea to "area";

insert into airport (code, country, city, area )
values ('MNK', 'Беларусь', 'Минск', 10),
       ('LDN', 'Англия', 'Лондон', 11),
       ('MSK', 'Россия', 'Москва', 11),
       ('BSL', 'Испания', 'Барселона', 6);

update airport set country = 'Ангиля' where code = 'LDN';

select * from airport;

delete from airport where country = 'Ангиля';

select * from airport;
