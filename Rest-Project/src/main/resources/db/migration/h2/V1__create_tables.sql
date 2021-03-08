create sequence user_id_seq start with 10 increment by 50;

create table users
(
    id         bigint default user_id_seq.nextval,
    username   varchar(255) not null,
    password   varchar(255) not null,
    created_at timestamp,
    updated_at timestamp,
    primary key (id),
    UNIQUE KEY user_name_unique (username)
);

create sequence account_id_seq start with 10 increment by 50;

create table accounts
(
    id             bigint DEFAULT account_id_seq.nextval,
    name           varchar(32) not null,
    account_number varchar(32) not null,
    balance        double,
    bonus          bigint,
    user_id        bigint,
    created_at     timestamp,
    updated_at     timestamp,
    primary key (id)
);

ALTER TABLE accounts
    ADD CONSTRAINT userp_fk FOREIGN KEY (user_id) REFERENCES users (id);

create sequence th_id_seq start with 100 increment by 1;

create table transaction_history
(
    id         bigint DEFAULT th_id_seq.nextval,
    name       varchar(32) not null,
    products   varchar(1500) not null,
    amount     double,
    status     varchar(32) not null,
    bonus      bigint,
    account_id bigint,
    user_id    bigint,
    created_at timestamp,
    rent_to    timestamp,
    updated_at timestamp,
    primary key (id)
);

ALTER TABLE transaction_history
    ADD CONSTRAINT userth_fk FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE transaction_history
    ADD CONSTRAINT account_fk FOREIGN KEY (account_id) REFERENCES accounts (id);

create table product_price
(
    id         bigint,
    name       varchar(32) not null,
    type       varchar(32) not null,
    amount     double,
    interval_price   bigint,
    bonus      bigint,
    primary key (id)
);


create sequence product_id_seq start with 10 increment by 50;

create table products
(
    id         bigint DEFAULT product_id_seq.nextval,
    name       varchar(32) not null,
    price_id   bigint,
    genre      varchar(32) not null,
    primary key (id)
);

ALTER TABLE products
    ADD CONSTRAINT productprice_fk FOREIGN KEY (price_id) REFERENCES product_price (id);