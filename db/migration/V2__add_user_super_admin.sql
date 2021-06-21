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

