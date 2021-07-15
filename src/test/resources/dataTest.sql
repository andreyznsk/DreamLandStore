INSERT INTO USERS
(username,
 password,
 first_name,
 last_name,
 email)
VALUES ('admin',
        '$2y$12$n7gF2VeEz4ST9MjvdroaBOVClYYO35naUzdr.iHW14Ll42r/JccS.',
        'Andrey',
        'Zaitsev',
        '1@1.ru');
INSERT INTO USERS
(username,
 password,
 first_name,
 last_name,
 email)
VALUES ('andrey',
        '$2y$12$n7gF2VeEz4ST9MjvdroaBOVClYYO35naUzdr.iHW14Ll42r/JccS.',
        'Andrey',
        'Zaitsev',
        '2@2.ru');

INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_SUPER_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_CLIENT');

insert into users_roles (user_id, role_id) VALUES (1,1);
insert into users_roles (user_id, role_id) VALUES (1,2);
insert into users_roles (user_id, role_id) VALUES (1,3);

insert into products(price, name) VALUES
(16.00,'карандаш'),
(17.00,'карандаш2'),
(18.00,'тетрадь'),
(20.00,'книга'),
(25.00,'резинка');

insert into chart (customer_id,prod_id) VALUES (1,1),
                                                (1,2);

insert into ORDERS (CUSTOMER_ID, DELIVERY_ADDRESS, IS_ORDERED, IS_PAID, IS_DELIVERED, ORDER_DATE, TOTAL_PRICE)
VALUES (1,'Nsk',false,false,false,'2021-01-01',10.1);

insert into ORDER_DETAILS (ORDER_ID, PROD_ID) VALUES ( 1,1);
