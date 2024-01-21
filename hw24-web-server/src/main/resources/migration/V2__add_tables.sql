-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence address_SEQ start with 1 increment by 1;
create sequence phone_SEQ start with 1 increment by 1;

create table address
(
    address_id   bigint not null primary key,
    street varchar(50)
);

create table phone
(
    id   bigint not null primary key,
    number varchar(50)
);