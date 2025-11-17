-- ParkingNest Database Schema
-- MySQL Database: parking_nest_db

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS parking_nest_db;
USE parking_nest_db;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_username (username),
    INDEX idx_email (email)
);

-- Future tables for parking reservation functionality
-- (These will be created in future iterations)

-- Parking spots table
CREATE TABLE IF NOT EXISTS parking_spots (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    spot_number VARCHAR(10) NOT NULL UNIQUE,
    location VARCHAR(100) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Reservations table
CREATE TABLE IF NOT EXISTS reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    parking_spot_id BIGINT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    status ENUM('ACTIVE', 'COMPLETED', 'CANCELLED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (parking_spot_id) REFERENCES parking_spots(id) ON DELETE CASCADE,
    
    INDEX idx_user_id (user_id),
    INDEX idx_parking_spot_id (parking_spot_id),
    INDEX idx_start_time (start_time),
    INDEX idx_status (status)
);

-- Insert sample parking spots
INSERT INTO parking_spots (spot_number, location) VALUES
('A001', 'Ground Floor - Section A'),
('A002', 'Ground Floor - Section A'),
('A003', 'Ground Floor - Section A'),
('B001', 'First Floor - Section B'),
('B002', 'First Floor - Section B'),
('B003', 'First Floor - Section B'),
('C001', 'Second Floor - Section C'),
('C002', 'Second Floor - Section C'),
('C003', 'Second Floor - Section C')
ON DUPLICATE KEY UPDATE spot_number = spot_number;