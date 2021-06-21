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
