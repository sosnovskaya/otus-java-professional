create table client
(
    id         bigserial not null primary key,
    name  varchar(50)
);

create table address
(
    id      bigserial   not null primary key,
    address varchar(300) not null,
    client_id bigint      not null references client (id)
);

create table phone
(
    id        bigserial   not null primary key,
    order_column    int         not null,
    number    varchar(30) not null,
    client_id bigint      not null references client (id)
);

