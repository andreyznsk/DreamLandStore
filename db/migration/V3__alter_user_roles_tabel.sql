DELETE FROM dreamland.flyway_schema_history WHERE installed_rank = '3';
USE dreamland;
CREATE INDEX users_name ON dreamland.users(username);
ALTER TABLE dreamland.users ADD UNIQUE (username,email);
ALTER TABLE dreamland.users_roles ADD CONSTRAINT roles_unique UNIQUE (user_id,role_id);

