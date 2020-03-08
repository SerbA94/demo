
CONNECT 'jdbc:mysql://${MYSQL_HOST:localhost}:3306/demo;create=true;user=root;password=Root1234';

DROP TABLE users;
DROP TABLE roles;

----------------------------------------------------------------
-- ROLES
-- users roles
----------------------------------------------------------------
CREATE TABLE roles(

-- id has the INTEGER type (other name is INT), it is the primary key
	id INTEGER NOT NULL PRIMARY KEY,

-- name has the VARCHAR type - a string with a variable length
-- names values should not be repeated (UNIQUE)
	name VARCHAR(10) NOT NULL UNIQUE
);

-- this two commands insert data into roles table
----------------------------------------------------------------
-- ATTENTION!!!
-- we use ENUM as the Role entity, so the numeration must started
-- from 0 with the step equaled to 1
----------------------------------------------------------------
INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'customer');

----------------------------------------------------------------
-- USERS
----------------------------------------------------------------
CREATE TABLE users(

-- 'generated always AS identity' means id is autoincrement field
-- (from 1 up to Integer.MAX_VALUE with the step 1)
	id INTEGER NOT NULL generated always AS identity PRIMARY KEY,

-- 'UNIQUE' means logins values should not be repeated in login column of table
	login VARCHAR(10) NOT NULL UNIQUE,

-- not null string columns
	password VARCHAR(10) NOT NULL,

-- this declaration contains the foreign key constraint
-- role_id in users table is associated with id in roles table
-- role_id of user = id of role
	role_id INTEGER NOT NULL REFERENCES roles(id)

-- removing a row with some ID from roles table implies removing
-- all rows from users table for which ROLES_ID=ID
-- default value is ON DELETE RESTRICT, it means you cannot remove
-- row with some ID from the roles table if there are rows in
-- users table with ROLES_ID=ID
		ON DELETE CASCADE

-- the same as previous but updating is used insted deleting
		ON UPDATE RESTRICT
);

-- id = 1
INSERT INTO users VALUES(DEFAULT, 'admin', 'admin', 0);
-- id = 2
INSERT INTO users VALUES(DEFAULT, 'customer', 'customer', 1);
-- id = 3
INSERT INTO users VALUES(DEFAULT, 'user', 'user', 1);




----------------------------------------------------------------
-- test database:
----------------------------------------------------------------

SELECT * FROM users;
SELECT * FROM roles;

DISCONNECT;