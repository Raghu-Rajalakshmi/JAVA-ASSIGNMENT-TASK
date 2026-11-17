CREATE DATABASE IF NOT EXISTS assignment5;
USE assignment5;
DROP TABLE IF EXISTS users;
CREATE TABLE users(user_id VARCHAR(60) PRIMARY KEY, password VARCHAR(100) NOT NULL);
INSERT INTO users VALUES ('admin','correct-password');
