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
    role ENUM('USER', 'ADMIN', 'LOT_MANAGER') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_username (username),
    INDEX idx_email (email)
);

-- Parking lots table
CREATE TABLE IF NOT EXISTS parking_lots (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lot_name VARCHAR(100) NOT NULL UNIQUE,
    location VARCHAR(200) NOT NULL,
    description TEXT,
    capacity INT NOT NULL,
    occupied_slots INT DEFAULT 0,
    status ENUM('AVAILABLE', 'MAINTENANCE', 'CLOSED') DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_lot_name (lot_name),
    INDEX idx_location (location),
    INDEX idx_status (status)
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
-- Insert default lot manager user
INSERT INTO users (username, password, email, first_name, last_name, role) VALUES
('lotm', '$2a$10$CwTycUXWue0Thq9StjUM0uYxvVT5C8jY6xq9HxSAKNrYz8BEqM7ni', 'lotmanager@parking.com', 'Lot', 'Manager', 'LOT_MANAGER')
ON DUPLICATE KEY UPDATE username = username;

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

-- Insert sample parking lots
INSERT INTO parking_lots (lot_name, location, description, capacity, occupied_slots, status) VALUES
('Garden Area - Ground Floor', 'Garden Area', 'Open parking space with green surroundings', 250, 0, 'AVAILABLE'),
('Main Building - Ground Floor', 'Main Building', 'Multi-level covered parking', 200, 20, 'AVAILABLE')
ON DUPLICATE KEY UPDATE lot_name = lot_name;