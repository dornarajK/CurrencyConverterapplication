-- 1. Drop and create database
DROP DATABASE IF EXISTS currencydb;
CREATE DATABASE currencydb;
USE currencydb;


DROP USER IF EXISTS 'appuser'@'localhost';


CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'app123';


GRANT SELECT, INSERT, UPDATE, DELETE ON currencydb.* TO 'appuser'@'localhost';

FLUSH PRIVILEGES;


CREATE TABLE Currency (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          abbreviation VARCHAR(10) NOT NULL UNIQUE,
                          name VARCHAR(50) NOT NULL,
                          rate_to_usd DECIMAL(10,6) NOT NULL
);


INSERT INTO Currency (abbreviation, name, rate_to_usd) VALUES
                                                           ('USD', 'US Dollar', 1.000000),
                                                           ('EUR', 'Euro', 0.920000),
                                                           ('GBP', 'British Pound', 0.790000),
                                                           ('JPY', 'Japanese Yen', 150.500000),
                                                           ('NOK', 'Norwegian Krone', 10.700000),
                                                           ('SEK', 'Swedish Krona', 10.900000),
                                                           ('INR', 'Indian Rupee', 83.200000),
                                                           ('NPR', 'Nepalese Rupee', 133.000000);