CREATE INDEX users_name ON users(username);
ALTER TABLE users ADD UNIQUE (username,email);
ALTER TABLE users_roles ADD CONSTRAINT roles_unique UNIQUE (user_id,role_id);

