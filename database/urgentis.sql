CREATE DATABASE IF NOT EXISTS urgentis;

USE urgentis;

CREATE TABLE IF NOT EXISTS patients (
    patient_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(20),
    severity VARCHAR(20) NOT NULL,
    status VARCHAR(30) DEFAULT 'WAITING',
    arrival_time DATETIME NOT NULL
);