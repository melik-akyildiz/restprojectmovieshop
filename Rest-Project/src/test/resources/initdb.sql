create sequence user_id_seq start with 10 increment by 50;

create table users (
                       id bigint DEFAULT nextval('user_id_seq') not null,
                       name varchar(255) not null CONSTRAINT user_name_unique UNIQUE,
                       password varchar(255) not null,
                       created_at timestamp,
                       updated_at timestamp,
                       primary key (id)
);

create sequence account_id_seq start with 10 increment by 50;

create table accounts (
                          id bigint DEFAULT nextval('account_id_seq') not null,
                          name varchar(32) not null,
                          account_number varchar(32) not null,
                          balance DOUBLE PRECISION,
                          user_id bigint,
                          bonus     bigint,
                          created_at timestamp,
                          updated_at timestamp,
                          primary key (id)
);
ALTER TABLE accounts ADD CONSTRAINT userp_fk FOREIGN KEY (user_id) REFERENCES users(id);

create table product_price
(
    id         bigint,
    name       varchar(32) not null,
    type       varchar(32) not null,
    amount     DOUBLE PRECISION,
    interval_price   bigint,
    bonus      bigint,
    primary key (id)
);

create sequence product_id_seq start with 10 increment by 50;

create table products
(
    id         bigint DEFAULT nextval(product_id_seq),
    name       varchar(32) not null,
    price_id   bigint,
    genre      varchar(32) not null,
    primary key (id)
);

create sequence transaction_history_id_seq start with 10 increment by 50;

create table transaction_history (
                                     id bigint DEFAULT nextval('transaction_history_id_seq') not null,
                                     name varchar(32) not null,
                                     type varchar(32) not null,
                                     account_id bigint,
                                     target_account_id bigint,
                                     balance DOUBLE PRECISION,
                                     debit DOUBLE PRECISION,
                                     credit DOUBLE PRECISION,
                                     user_id bigint,
                                     target_user_id bigint,
                                     created_at timestamp,
                                     updated_at timestamp,
                                     primary key (id)
);
ALTER TABLE transaction_history
    ADD CONSTRAINT userth_fk FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE transaction_history
    ADD CONSTRAINT account_fk FOREIGN KEY (account_id) REFERENCES accounts(id);
ALTER TABLE transaction_history
    ADD CONSTRAINT target_account_fk FOREIGN KEY (target_account_id) REFERENCES accounts(id);


INSERT INTO users (username, password, created_at)
VALUES ('Admin', '$2a$10$Zv4t9I8rV6SOVAlS3pzpPeZQISj6l22JpLDMykr969f39l3rTXX0y', current_timestamp),
       ('DefaultUser', '$2a$10$Zv4t9I8rV6SOVAlS3pzpPeZQISj6l22JpLDMykr969f39l3rTXX0y', current_timestamp),
       ('Melik', '$2a$10$Zv4t9I8rV6SOVAlS3pzpPeZQISj6l22JpLDMykr969f39l3rTXX0y', current_timestamp);



INSERT INTO accounts (name, created_at, user_id, account_number, balance, bonus)
VALUES ('Admin', current_timestamp, 10, '4539498683289332', '111', 0),
       ('Melik AKYILDIZ', current_timestamp, 60, '5473764073701185', '121', 0),
       ('Melik Akyildiz', current_timestamp, 60, '4485624568387461', '131', 0);


INSERT INTO product_price (id, name, type, amount, interval_price, bonus)
VALUES (1, 'New releases', 'premium_price', 40, 1, 2),
       (2, 'Regular films', 'basic_price', 30, 3, 1),
       (3, 'Old film', 'basic_price', 30, 5, 1);

INSERT INTO products (name, genre, price_id)
VALUES ('movie New releases 1', 'Adventures', 1),
       ('movie New releases 2', 'Adventures', 1),
       ('movie Regular films 3', 'Adventures', 2),
       ('movie Regular films 4', 'Adventures', 2),
       ('movie Regular films 5', 'Adventures', 2),
       ('movie Regular films 6', 'Adventures', 2),
       ('movie Old films 7', 'Adventures', 3),
       ('movie Old films 8', 'Adventures', 3),
       ('movie Old films 9', 'Adventures', 3),
       ('movie Old films 10', 'Adventures', 3),
       ('movie Old films 11', 'Adventures', 3),
       ('movie Old films 12', 'Adventures', 3),
       ('movie Old films 13', 'Adventures', 3),
       ('movie Old films 14', 'Adventures', 3),
       ('movie Old films 15', 'Adventures', 3),
       ('movie New releases 16', 'Sci-Fi', 1),
       ('movie New releases 17', 'Sci-Fi', 1),
       ('movie New releases 18', 'Sci-Fi', 1),
       ('movie Regular films 19', 'Sci-Fi', 2),
       ('movie Regular films 20', 'Sci-Fi', 2),
       ('movie Old films 21', 'Sci-Fi', 3);
