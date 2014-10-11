-- Create DevNote database;
-- Data base structure version = 2.1;

-- DEVNOTE USER CREATE
-- !!!WARNING!!! Change password here!
CREATE USER 'devnote'@'localhost' IDENTIFIED BY 'secret';

-- DEVNOTE DATABASE CREATE
CREATE DATABASE `devnote` CHARACTER SET utf8 COLLATE utf8_general_ci;

-- GRANT RIGHTS TO DB USER
GRANT ALL ON devnote.* TO 'devnote'@'localhost';

-- CHANGE DB;
USE devnote;

-- USER TABLE
CREATE TABLE User (
id		int AUTO_INCREMENT not null,
login		varchar(255) not null,
passw		varchar(32) not null,
crt_date	timestamp not null,
log_date	timestamp not null,
is_admin	boolean not null,
is_enabled	boolean not null,
PRIMARY KEY (id),
UNIQUE (login)
);

-- NOTE DIRECTORY TABLE
CREATE TABLE Directory (
id		int AUTO_INCREMENT not null,
path		text(600) not null,
user_id		int not null,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES User(id)
);

-- NOTE TABLE
CREATE TABLE Note (
id		int AUTO_INCREMENT not null,
header		text(500) not null,
dir_id		int not null,
user_id		int not null,
last_version_id	int not null,
last_version_date timestamp not null,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES User(id),
FOREIGN KEY (dir_id) REFERENCES Directory(id)
);

-- NOTE VERSION TABLE
CREATE TABLE Version (
id		int AUTO_INCREMENT not null,
hash		text(32) not null,
save_date	timestamp not null,
user_id		int not null,
note_id		int not null,
note_text	mediumtext not null,
PRIMARY KEY (id),
FOREIGN KEY (user_id) REFERENCES User(id),
FOREIGN KEY (note_id) REFERENCES Note(id)
);

-- ADD FOREIGN KEY FOR NOTE TABLE
ALTER TABLE Note ADD FOREIGN KEY (last_version_id) REFERENCES Version(id);