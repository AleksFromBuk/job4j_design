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
values ('MNK', '��������', '�����', 10),
       ('LDN', '������', '������', 11),
       ('MSK', '������', '������', 11),
       ('BSL', '�������', '���������', 6);

update airport set country = '������' where code = 'LDN';

select * from airport;

delete from airport where country = '������';

select * from airport;
