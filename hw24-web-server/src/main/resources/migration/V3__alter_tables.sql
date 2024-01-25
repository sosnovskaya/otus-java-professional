alter table client
    add address_id bigint unique references address;


alter table phone
    add client_id  bigint references client;