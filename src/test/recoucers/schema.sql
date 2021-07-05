drop table if exists USERS cascade;
drop table if exists  USERS_ROLES cascade;
drop table if exists  ROLE cascade;
drop table if exists  PRODUCTS cascade;
DROP table if exists  CHART cascade;

CREATE TABLE users (
                       id         IDENTITY NOT NULL,
                       username   VARCHAR(50)  NOT NULL,
                       password   VARCHAR(100) NOT NULL,
                       first_name VARCHAR(100) NOT NULL,
                       last_name  VARCHAR(100) NOT NULL,
                       email      VARCHAR(100),
                       PRIMARY KEY (id)
);

CREATE TABLE role (
                      id        INT        IDENTITY NOT NULL,
                      name VARCHAR(50) NOT NULL,
                      PRIMARY KEY (id)

);

CREATE TABLE users_roles (
                             user_id INT NOT NULL,
                             role_id INT NOT NULL,
                             FOREIGN KEY (user_id) REFERENCES users(id),
                             FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE INDEX users_name ON users(username);
ALTER TABLE users ADD UNIQUE (username,email);
ALTER TABLE users_roles ADD CONSTRAINT roles_unique UNIQUE (user_id,role_id);

CREATE TABLE products (
                          prod_id IDENTITY PRIMARY KEY,
                          price numeric(6,2),
                          name varchar(50)

);

CREATE INDEX products_name ON products(name);

CREATE TABLE chart (
                       id INT          IDENTITY NOT NULL,
                       customer_id INT NOT NULL,
                       prod_id INT NOT NULL,
                       FOREIGN KEY (customer_id) REFERENCES USERS(id),
                       FOREIGN KEY (prod_id) REFERENCES PRODUCTS(prod_id)

);
