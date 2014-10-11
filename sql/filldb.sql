-- Filling up DevNote database structure;
-- Data base structure version = 0.1; 

-- CHANGE DB;
USE devnote;

-- USER CREATION

-- CREATE ADMIN (id = 1);
INSERT INTO User (login,passw,crt_date,log_date,is_admin,is_enabled) VALUES("admin", MD5('admin'), NOW(), NOW(), TRUE, TRUE);

-- CREATE REGULAR USER (id = 2)
INSERT INTO User (login,passw,crt_date,log_date,is_admin,is_enabled) VALUES("user", MD5('user'), NOW(), NOW(), FALSE, TRUE);

-- DIRECTORY CREATION

-- ID = 1
INSERT INTO Directory (path, user_id) VALUES ("Test", 2);

-- ID = 2
INSERT INTO Directory (path, user_id) VALUES ("Test.Inner", 2);

-- ID = 3
INSERT INTO Directory (path, user_id) VALUES ("Test.Inner.Deep", 2);

-- ID = 4
INSERT INTO Directory (path, user_id) VALUES ("Test2", 2);

-- NOTE CREATION

-- TEST NOTE (ID = 1)
INSERT INTO Note (header, dir_id, user_id, last_version_id, last_version_date) VALUES ("Test root note", 1, 2, 1, NOW());

-- TEST.INNER.DEEP NOTE (ID = 2)
INSERT INTO Note (header, dir_id, user_id, last_version_id, last_version_date) VALUES ("Test deep note", 3, 2, 2, NOW());

-- VERSION CREATION

-- TEST NOTE VERSION (ID = 1)
INSERT INTO Version (hash, save_date, user_id, note_id, note_text) VALUES (MD5("This is test note in Test directory!"), NOW(), 2, 1, "This is test note in Test directory!");

-- TEST.INNER.DEEP NOTE VERSION (ID = 2)
INSERT INTO Version (hash, save_date, user_id, note_id, note_text) VALUES (MD5("This is test note in Test/Innner/Deep directory!"), NOW(), 2, 2, "This is test note in Test directory!");