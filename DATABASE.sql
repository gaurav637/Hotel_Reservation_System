CREATE DATABASE hotel_db;
USE hotel_db;

CREATE TABLE reservation(
 reservation_id INT PRIMARY KEY auto_increment,
 guest_name VARCHAR(32),
 room_number VARCHAR(30),
 contact_number VARCHAR(22),
 reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
SELECT * FROM reservation;
CREATE TABLE security(
  name VARCHAR(30)NOT NULL,
  last VARCHAR(30)NOT NULL,
  email VARCHAR(30) NOT NULL,
  password INT PRIMARY KEY NOT NULL
);
SELECT * FROM security;